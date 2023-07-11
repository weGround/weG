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
import android.widget.TextView
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
import com.example.weg.data.PostDataSource
import com.example.weg.data.Result
import com.example.weg.databinding.FragmentGroundBinding


class GroundFragment : Fragment(), PostRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentGroundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: PostRecyclerAdapter;

    val postDataSource = PostDataSource();

    var postList : ArrayList<PostRecyclerItem> = ArrayList<PostRecyclerItem>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGroundBinding.inflate(inflater, container, false)
        val root: View = binding.root;

        val mainActivity = activity as MainActivity;
        postDataSource.getAllPost(mainActivity.getCurrentGroup()){
            if(it is Result.Success){
                postList.clear();
                for(item in it.data){
                    postList.add(item);
                }
            }
            activity?.runOnUiThread {
                updatePostRecyclerView();
            }
        }

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

    private fun updatePostRecyclerView(){
        binding.postRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView = binding.postRecycler;
        val mainActivity = activity as MainActivity;
        adapter = PostRecyclerAdapter(mainActivity.getUserId(), postList, this);
        Log.d("TAG", "this is the size of groupMemList : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context);
        recyclerView.adapter = adapter;
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
        val dialogFragment = PostDialogFragment(this);
        dialogFragment.show(childFragmentManager, "Post Dialog")
    }
    override fun onItemClick(position: Int) {
        // TODO("Not yet implemented")
    }
    override fun onLikeClick(
        position: Int,
        clickedPost: PostRecyclerItem,
        postLikeView: ImageButton,
        postLikeNumView: TextView
    ) {
        val activityMain = activity as MainActivity;
        val userId = activityMain.getUserId()!!;
        if(clickedPost.isLiked(activityMain.getUserId()!!)){
            clickedPost.deleteLikeUsers(userId);
            val temp:Int = postLikeNumView.text.toString().toInt() - 1;
            postLikeNumView.text = temp.toString();
            postLikeView.setImageDrawable(ContextCompat.getDrawable(activityMain, R.drawable.ic_like_outlined)!!)
        }else {
            clickedPost.addLikeUsers(userId);
            val temp:Int = postLikeNumView.text.toString().toInt() + 1;
            postLikeNumView.text = temp.toString();
            postLikeView.setImageDrawable(ContextCompat.getDrawable(activityMain, R.drawable.ic_like_fill)!!)
        }

    }
    fun addNewPost(postTitle:String, postContent:String){
        val mainActivity = activity as MainActivity;
        postDataSource.addNewPost(mainActivity.getUserId(), postTitle, postContent) {
            if(it is Result.Success){
                postList.add(it.data);
                updatePostRecyclerView();
            }
        }
    }
}