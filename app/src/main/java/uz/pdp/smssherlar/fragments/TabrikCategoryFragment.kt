package uz.pdp.smssherlar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tabrik_category.view.*
import uz.pdp.smssherlar.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TabrikCategoryFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_tabrik_category, container, false)

        root.linear_back_tabrik_category.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, HomeFragment())
                ?.addToBackStack(HomeFragment.toString())
                ?.commit()
        }
        root.birthday_card.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, TabrikFragment())
                ?.addToBackStack(TabrikFragment.toString())
                ?.commit()
        }

        root.juma_card.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, JumaMuborakFragment())
                ?.addToBackStack(JumaMuborakFragment.toString())
                ?.commit()
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabrikCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}