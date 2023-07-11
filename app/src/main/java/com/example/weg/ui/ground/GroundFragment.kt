package com.example.weg.ui.ground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.MainActivity
import com.example.weg.R
import com.example.weg.databinding.FragmentGroundBinding


class GroundFragment : Fragment(), PostRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentGroundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: PostRecyclerAdapter;

    var postList : ArrayList<PostRecyclerItem> = ArrayList<PostRecyclerItem>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGroundBinding.inflate(inflater, container, false)
        val root: View = binding.root;
        postList.add(PostRecyclerItem("Jihwan", "첫번째 일기", "집에 보내주세요!", arrayListOf("Apple", "Banana", "Orange")));
        postList.add(PostRecyclerItem("Yejin", "두번째 일기", "집에 보내주세요!", arrayListOf("Apple", "Banana", "Orange")));
        postList.add(PostRecyclerItem("Jihwan", "첫번째 일기", "집에 보내주세요!", arrayListOf("Apple", "Banana", "Orange")));
        postList.add(PostRecyclerItem("Yejin", "두번째 일기", "집에 보내주세요!", arrayListOf("Apple", "Banana", "Orange")));

        binding.postRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView = binding.postRecycler;
        adapter = PostRecyclerAdapter(postList, this);
        Log.d("TAG", "this is the size of groupMemList : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context);
        recyclerView.adapter = adapter;

        val mainActivity = activity as MainActivity;

        val toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)
        val menu = toolbar.menu
        val item = menu.findItem(R.id.add_action)
        if (item != null) {
            item.isVisible = true
        }
        val menuhost: MenuHost = requireActivity()
        initMenu(menuhost)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMenu(menuhost: MenuHost) {
        menuhost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val actionBar = (activity as AppCompatActivity).supportActionBar
                actionBar?.setDisplayHomeAsUpEnabled(false)
                actionBar?.title = "Contacts"

                menuInflater.inflate(R.menu.actionbar_main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.add_action) {
                    onAddClick();
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun onAddClick(){
        Log.d("heyhey", "onAddClick: ");
    }
    override fun onItemClick(position: Int) {
        // TODO("Not yet implemented")
    }
    override fun onLikeClick(
        position: Int,
        clickedPost: PostRecyclerItem,
        postLikeView: ImageButton
    ) {
        val activityMain = activity as MainActivity;
        val userId = activityMain.getUserId()!!;
        if(clickedPost.isLiked(activityMain.getUserId()!!)){
            clickedPost.deleteLikeUsers(userId);
            postLikeView.setImageDrawable(ContextCompat.getDrawable(activityMain, R.drawable.ic_like_outlined)!!)
        }else {
            clickedPost.addLikeUsers(userId);
            postLikeView.setImageDrawable(ContextCompat.getDrawable(activityMain, R.drawable.ic_like_fill)!!)
        }

    }
}