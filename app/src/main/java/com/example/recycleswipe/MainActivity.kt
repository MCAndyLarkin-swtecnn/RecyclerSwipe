package com.example.recycleswipe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resyContact: RecyclerView = findViewById(R.id.contact_list)
        resyContact.layoutManager = LinearLayoutManager(this)
        resyContact.adapter = SwipeAdapter(createTestList())
//        (resyContact.adapter as SwipeAdapter).notifyDataSetChanged()
        resyContact.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.e("Scroll", "Recy")
            if(abs(scrollY - oldScrollY) > SwipeAdapter.const.SCROLL_REACTION){
                val first = (resyContact.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val last = (resyContact.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                for (position in first..last){
                    (resyContact.layoutManager as LinearLayoutManager).findViewByPosition(position)?.let{
                        (it as SwipeView).hide();
                    }
                }
            }
        }
                                                                                                    //How to expand recycle onTouchListener
    }
}
fun createTestList(): List<ContactData> =                                                           //Why I cant move file while indexing in process
    listOf(
            ContactData(
                    phoneNumber = "89876543210",
                    photo = R.drawable.xz4,
                    lastCall = "1.23.4567",
                    operator = "Beeline"
            ),
            ContactData(
                    nameSurname = "Maxim Sokolov",
                    photo = R.drawable.xz2,
                    operator = "Yota",
                    lastCall = "No last"
            ),
            ContactData(
                    nameSurname = "Vladimir Putin",
                    phoneNumber = "Сам позвонит",
                    lastCall = "No info",
                    operator = "EdinayaRossiya"
            ),
            ContactData(
                    nameSurname = "Pavel Durov",
                    phoneNumber = "28347562304",
                    photo = R.drawable.xz1,
                    lastCall = "2.02.2020"
            ),
            ContactData(
                    nameSurname = "Elon Musk",
                    phoneNumber = "88005553535",
                    photo = R.drawable.xz3,
                    operator = "Tesla"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            ),
            ContactData(
                    nameSurname = "Jesus Christos",
                    phoneNumber = "2000-7-01",
                    photo = R.drawable.jesus,
                    lastCall = "IN - 33y AC",
                    operator = "Cercov'"
            )
    )