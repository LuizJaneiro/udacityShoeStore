package com.udacity.shoestore.shoe_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.ShoeListViewModel
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.models.Shoe

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var viewModel: ShoeListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(ShoeListViewModel::class.java)
        setupListeners()
        setupObservers()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupListeners() {
        binding.detailsFloatingActionButton.setOnClickListener {
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        }
    }

    private fun setupObservers() {
        viewModel.shoesData.observe(viewLifecycleOwner, Observer { shoes ->
            binding.shoeListLinearLayout.removeAllViews()
            shoes.forEach {
                binding.shoeListLinearLayout.addView(createShoeView(it))
            }
        })
    }

    private fun createShoeView(shoe: Shoe): View {
        val nameTextView = AppCompatTextView(requireContext())
        val companyTextView = AppCompatTextView(requireContext())
        val sizeTextView = AppCompatTextView(requireContext())
        val descriptionTextView = AppCompatTextView(requireContext())
        val marginSmall = (requireContext().resources.getDimension(R.dimen.margin_small) / requireContext().resources.displayMetrics.density).toInt()
        val textSizeNormal = (requireContext().resources.getDimension(R.dimen.text_medium) / requireContext().resources.displayMetrics.density)


        descriptionTextView.text = shoe.description

        val llShoeRoot = LinearLayoutCompat(requireContext())
        llShoeRoot.apply {
            id = View.generateViewId()
            gravity = Gravity.CENTER_VERTICAL
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                updatePadding(
                    left = marginSmall,
                    top = marginSmall,
                    right = marginSmall,
                    bottom = marginSmall
                )
            }
        }

        nameTextView.apply {
            id = View.generateViewId()
            gravity = Gravity.CENTER_HORIZONTAL
            textAlignment = AppCompatTextView.TEXT_ALIGNMENT_CENTER
            TextViewCompat.setTextAppearance(this, R.style.TextTitle)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                updatePadding(
                    bottom = marginSmall
                )
            }
            text = requireContext().getString(R.string.name_description, shoe.name)
        }
        llShoeRoot.addView(nameTextView)

        val llShoeCompanyAndSize = LinearLayoutCompat(requireContext())
        llShoeCompanyAndSize.apply {
            id = View.generateViewId()
            orientation = LinearLayoutCompat.HORIZONTAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        companyTextView.apply {
            id = View.generateViewId()
            textSize = textSizeNormal
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                updatePadding(
                    bottom = marginSmall,
                    right = marginSmall
                )
            }
            text = requireContext().getString(R.string.company_description, shoe.company)
        }
        llShoeCompanyAndSize.addView(companyTextView)

        sizeTextView.apply {
            id = View.generateViewId()
            textSize = textSizeNormal
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                updatePadding(
                    bottom = marginSmall,
                    left = marginSmall
                )
            }
            text = requireContext().getString(R.string.size_description, shoe.size.toString())
        }
        llShoeCompanyAndSize.addView(sizeTextView)
        llShoeRoot.addView(llShoeCompanyAndSize)

        descriptionTextView.apply {
            id = View.generateViewId()
            textSize = textSizeNormal
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                updatePadding(
                    bottom = marginSmall
                )
            }
            text = requireContext().getString(R.string.description_description, shoe.description)
        }
        llShoeRoot.addView(descriptionTextView)

        return llShoeRoot
    }
}