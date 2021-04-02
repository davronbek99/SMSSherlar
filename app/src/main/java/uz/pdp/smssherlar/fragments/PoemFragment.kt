package uz.pdp.smssherlar.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.fragment_poem.view.*
import kotlinx.android.synthetic.main.item_poem_dialog.view.*
import uz.pdp.smssherlar.R
import uz.pdp.smssherlar.adapters.PoemAdapter
import uz.pdp.smssherlar.db.AppDatabase
import uz.pdp.smssherlar.db.PoemEntity
import uz.pdp.smssherlar.models.PoemModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PoemFragment : Fragment() {
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
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var poemAdapter: PoemAdapter
    private lateinit var list: ArrayList<PoemModel>
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_poem, container, false)
        myClipboard = root.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?;
        val poemDao = AppDatabase.gatDatabase(root.context).poemDao()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("all/love")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list = ArrayList()
                for (child in snapshot.children) {
                    val value = child.getValue(PoemModel::class.java)
                    list.add(value!!)
                }

                root.linear_back.setOnClickListener {
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.container, HomeFragment())
                        ?.addToBackStack(HomeFragment.toString())
                        ?.commit()
                }
                poemAdapter = PoemAdapter(list, object : PoemAdapter.OnClickListener {
                    override fun onItemClickListener(poemModel: PoemModel, position: Int) {
                        val alertDialog = AlertDialog.Builder(root.context).create()
                        val view = inflater.inflate(R.layout.item_poem_dialog, null)
                        alertDialog.setView(view)
                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.window?.setGravity(Gravity.BOTTOM)
                        view.poem_name_info_tv.text = poemModel.name
                        view.poem_desc_info_tv.text = poemModel.desc

                        view.card_sms_dialog.setOnClickListener {
                            val uri = Uri.parse("smsto:")
                            val intent = Intent(Intent.ACTION_SENDTO, uri)
                            intent.putExtra("sms_body", poemModel.desc)
                            startActivity(intent)
                        }

                        var poemEntity = poemDao.getPoemById(poemModel.id!!)
                        if (poemEntity != null) {
                            view.like.isLiked = true
                        }
                        view.like.setOnLikeListener(object : OnLikeListener {
                            override fun liked(likeButton: LikeButton?) {
                                poemEntity = PoemEntity()
                                poemEntity.id = poemModel.id
                                poemEntity.name = poemModel.name
                                poemEntity.desc = poemModel.desc
                                poemDao.insertPoem(poemEntity)
                                poemAdapter.notifyItemChanged(position)
                            }

                            override fun unLiked(likeButton: LikeButton?) {
                                poemDao.deletePoem(poemEntity)
                            }

                        })

                        view.card_share_dialog.setOnClickListener {
                            val intent = Intent()
                            intent.action = Intent.ACTION_SEND
                            intent.putExtra(Intent.EXTRA_TEXT, poemEntity.desc)
                            intent.type = "text/plain"
                            startActivity(Intent.createChooser(intent, "Share To:"))
                        }

                        view.card_copy_dialog.setOnClickListener {
//                            val clipboard =
//                                root.context.getSystemService(
//                                    CLIPBOARD_SERVICE
//                                ) as ClipboardManager?
//                            val getstring: String = poemEntity.desc.toString()
                            setClipboard(root.context, poemEntity.desc.toString())
                        }
                        alertDialog.show()
                    }

                })
                root.poem_rv.adapter = poemAdapter

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return root
    }

    private fun setClipboard(context: Context, text: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = text
        } else {
            val clipboard =
                context.getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text)
            clipboard.setPrimaryClip(clip)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PoemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}