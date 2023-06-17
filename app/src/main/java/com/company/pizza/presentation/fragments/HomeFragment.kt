package com.company.pizza.presentation.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.pizza.R
import com.company.pizza.databinding.FragmentHomeBinding
import com.company.pizza.domain.util.Resource
import com.company.pizza.presentation.HomeViewModel
import com.company.pizza.presentation.adapters.CategoryAdapter
import com.company.pizza.presentation.adapters.PizzaAdapter
import com.company.pizza.presentation.adapters.SliderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), CategoryAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val pizzaAdapter = PizzaAdapter()
    private val sliderAdapter = SliderAdapter()
    private val categoryAdapter = CategoryAdapter(this)
    private lateinit var viewModel: HomeViewModel
    private var prevClick: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val tmpViewModel by viewModels<HomeViewModel>()
        viewModel = tmpViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.menu.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    pizzaAdapter.pizzaList = resource.data!!
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> showProgressBar()
            }

        }
    }

    private fun setupRecycler() {
        binding.apply {
            pizzaRv.adapter = pizzaAdapter
            pizzaRv.layoutManager = LinearLayoutManager(activity)
            pizzaRv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            slider.adapter = sliderAdapter
            slider.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            categoryRv.adapter = categoryAdapter
            categoryRv.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onItemClick(view: View) {
        val cardText = view.findViewById<TextView>(R.id.category_text)
        cardText.setTextColor((ContextCompat.getColor(requireContext(), R.color.red)))
        cardText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_bg))
        cardText.typeface = Typeface.DEFAULT_BOLD
        if (prevClick != null && prevClick != view) {
            val prevCardText = prevClick!!.findViewById<TextView>(R.id.category_text)
            prevCardText.setTextColor((ContextCompat.getColor(requireContext(), R.color.black)))
            prevCardText.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_bg
                )
            )
            prevCardText.typeface = Typeface.DEFAULT
        }
        prevClick = view
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}