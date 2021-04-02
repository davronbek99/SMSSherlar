package uz.pdp.smssherlar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_home.view.*
import uz.pdp.smssherlar.R
import uz.pdp.smssherlar.db.AppDatabase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

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
        root = inflater.inflate(R.layout.fragment_home, container, false)

        AppDatabase.gatDatabase(root.context).poemDao().getAllPoem().observe(viewLifecycleOwner,
            Observer {
                root.save_poem_count_tv.text = it.size.toString()
            })
        root.card_like.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, FavouriteFragment())
                ?.addToBackStack(FavouriteFragment.toString())
                ?.commit()
        }

        root.card_new.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, NewsFragment())
                ?.addToBackStack(NewsFragment.toString())
                ?.commit()
        }

        root.linear_love_poems.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, PoemFragment())?.addToBackStack(PoemFragment.toString())
                ?.commit()
        }

        root.linear_miss.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, BoredomFragment())
                ?.addToBackStack(BoredomFragment.toString())
                ?.commit()
        }

        root.linear_conrolate_poems.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, TabrikCategoryFragment())
                ?.addToBackStack(TabrikCategoryFragment.toString())
                ?.commit()
        }

        root.linear_about_parent.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, ParentFragment())
                ?.addToBackStack(ParentFragment.toString())
                ?.commit()
        }

        root.linear_friendly_poems.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, FriendFragment())
                ?.addToBackStack(FriendFragment.toString())
                ?.commit()
        }

        root.linear_joke_poems.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, JokeFragmentFragment())
                ?.addToBackStack(JokeFragmentFragment.toString())
                ?.commit()
        }


        return root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}