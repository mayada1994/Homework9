package com.example.mayada.chatter

import android.content.Context
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.mayada.chatter.data.db.Message

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var messages: ArrayList<Message> = ArrayList()
    private var listener: OnItemClickListener? = null
    companion object {
        const val TYPE_FIRST_USER = 1
        const val TYPE_SECOND_USER = 2
    }
    interface OnItemClickListener {
        fun onItemClick(message: Message, view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutResource: Int
        when (viewType) {

            TYPE_FIRST_USER -> layoutResource = R.layout.first_user_message
            else -> layoutResource = R.layout.second_user_message
        }

        val view: View = LayoutInflater.from(viewGroup.context).inflate(layoutResource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.messageText.text = messages[position].messageText

        val clickListener = View.OnLongClickListener { view ->
            when (view.id) {
                R.id.message_text -> {
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener!!.onItemClick(messages[position], view)
                    }
                    return@OnLongClickListener true
                }
                else -> throw ExceptionInInitializerError()
            }
        }

        viewHolder.messageText.setOnLongClickListener(clickListener)
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (messages[position].messageUser) {
            1 -> TYPE_FIRST_USER
            else -> TYPE_SECOND_USER
        }
        return type
    }

    override fun getItemCount(): Int {
        return messages.size
    }

//    fun postMessage(message: Message) {
//        messages.add(message)
//        this.notifyItemInserted(messages.size - 1)
//    }

    fun setMessagesArray(currentMessages: List<Message>) {
        messages = currentMessages as ArrayList<Message>
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

          var messageText: TextView = itemView.findViewById(R.id.message_text)
          var editText: TextView = itemView.findViewById(R.id.edit_current_text)
//        lateinit var currentContext: Context
//
//        fun editItem(view: View?) {
//            if (view != null) {
//                messageText.visibility = View.GONE
//                editText.text = messageText.text.toString()
//                editText.visibility = View.VISIBLE
//                editText.requestFocus()
//                showKeyboard()
//                editText.imeOptions = EditorInfo.IME_ACTION_DONE
//                editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
//                editText.setOnEditorActionListener { view, actionId, event ->
//                    return@setOnEditorActionListener when (actionId) {
//                        EditorInfo.IME_ACTION_DONE -> {
//                            messageText.text = editText.text
//                            editText.visibility = View.GONE
//                            messageText.visibility = View.VISIBLE
//                            messages[adapterPosition].messageText = messageText.text.toString()
//                            editText.text = ""
//                            notifyItemChanged(adapterPosition)
//                            true
//                        }
//                        else -> false
//                    }
//                }
//            }
//        }
//
//        private fun showKeyboard() {
//            editText.postDelayed({
//                val keyboard = currentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//                keyboard!!.showSoftInput(editText, 0)
//            }, 100)
//        }
    }
}