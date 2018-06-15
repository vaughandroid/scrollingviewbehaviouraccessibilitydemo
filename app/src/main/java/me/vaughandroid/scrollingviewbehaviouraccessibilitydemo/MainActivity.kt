package me.vaughandroid.scrollingviewbehaviouraccessibilitydemo

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_issue_1.*

class MainActivity : AppCompatActivity() {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = sectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val layoutRes = when (position) {
                0 -> R.layout.fragment_issue_1
                1 -> R.layout.fragment_issue_2
                else -> throw Error()

            }
            return IssueFragment.forLayout(layoutRes)
        }

        override fun getCount(): Int = 2

    }

    class IssueFragment : Fragment() {

        companion object {

            private val EXTRA_LAYOUT_ID = "layout-id"

            fun forLayout(@LayoutRes layoutRes: Int): IssueFragment =
                    IssueFragment().apply {
                        arguments = Bundle().apply { putInt(EXTRA_LAYOUT_ID, layoutRes) }
                    }

        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val layoutId = arguments?.getInt(EXTRA_LAYOUT_ID) ?: throw Error()
            return inflater.inflate(layoutId, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            recycler_view.apply {
                layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                adapter = MyAdapter()
            }
        }
    }


    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun getItemCount(): Int = 100

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val textView: TextView = holder.itemView.findViewById(android.R.id.text1)
            textView.text = "Item $position"
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}
