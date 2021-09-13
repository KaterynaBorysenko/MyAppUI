package com.example.myappui
import android.app.ActionBar
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.GridLayout
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(),

    View.OnClickListener,
    AddCardDialogFragment.AddCardDialogListener{

    private lateinit var cD:CardDeck
    private lateinit var sA:SavedArea
    private lateinit var addBtn:FloatingActionButton
    private lateinit var clearBtn:FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cD=findViewById(R.id. cD)
        sA=findViewById(R.id.sA)
        addBtn=findViewById(R.id.addBtn)
        clearBtn=findViewById(R.id.clearBtn)

        cD.onSwipeRightListener=removeCard

        cD.onSwipeLeftListener=addCardTosA

        sA.listener=returnViewTocD

        addBtn.setOnClickListener(this)
        clearBtnsA.setOnClickListener(this)
        initCardDeck() }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.addBtn->addCard()
            R.id.clearBtn->clearsA()

        }
    }
    private fun initCardDeck(){
        initCardDeck.addView(CItem(this).also{
            it.bindCard(
                Card(
                    "Some info1",
                    R.drawable.picture1
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info2",
                    R.drawable.picture2
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info3",
                    R.drawable.picture3
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(

                    "Some info4",
                    R.drawable.picture4
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info5",
                    R.drawable.picture5
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info6",
                    R.drawable.picture6
                )
            )
        })

        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info7",
                    R.drawable.picture7
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info8",
                    R.drawable.picture8
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info9",
                    R.drawable.picture9
                )
            )
        })
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    "Some info10",
                    R.drawable.picture10
                )
            )
        })
    }

    private fun clearSavedArea() {
        val list = mutableListOf<View>()
        for (item in sA.children) {
            list.add(item)
        }
        sA.removeAllViews()
        for (item in list) {
            (item as CItem).onlyImage = false
            item.layoutParams = ActionBar.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            item.isLongClickable = false
            cD.addView(item)
        }
    }

    private fun addCard() {
        if (cD.childCount + sA.childCount >= cD.maxCardsNum) {
            Snackbar.make(cD, "Maximum card count!", Snackbar.LENGTH_SHORT).show()
            return }
        val dialog = AddCardDialogFragment()
        dialog.show(supportFragmentManager, "AddCardDialog")
    }
    override fun onDialogPositiveClick(title: String, description: String, image: String) {
        cD.addView(CItem(this).also {
            it.bindCard(
                Card(
                    title,
                    description,
                    when (image) {
                        "Picture 1" -> R.drawable.picture1
                        "Picture 2" -> R.drawable.picture2
                        "Picture 3" -> R.drawable.picture3
                        "Picture 4" -> R.drawable.picture4
                        "Picture 5" -> R.drawable.picture5
                        "Picture 6" -> R.drawable.picture6
                        "Picture 7" -> R.drawable.picture7
                        "Picture 8" -> R.drawable.picture8
                        "Picture 9" -> R.drawable.picture9
                        "Picture 10" -> R.drawable.picture10
                        else -> -1
                    }
                )
            )
        })
    }

    private val removeCard: (View) -> Unit = {
        AlertDialog.Builder(this)
            .setTitle("Remove card")
            .setMessage("Do you want to remove this card?")
            .setPositiveButton("Yes") { _, _ -> cD.removeView(it) }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }
    private val returnViewToTheDeck: (View) -> Unit = {
        AlertDialog.Builder(this)
            .setTitle("Return card")
            .setMessage("Do you want to return this card to the deck?")
            .setPositiveButton("Yes") { _, _ ->
                sA.removeView(it)
                (it as CItem).onlyImage = false
                it.layoutParams = ActionBar.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                it.isLongClickable = false
                cD.addView(it)
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }
    private val addCardToSavedArea: (View) -> Unit = {
        val view = it as CItem
        view.onlyImage = true
        cD.removeView(view)

        view.elevation = 2f
        val param = GridLayout.LayoutParams()
        with(param) {
            bottomMargin = 3
            topMargin = 4
            leftMargin = 2
            rightMargin = 3

            width = (sA.width - (rightMargin + leftMargin) * 2) / 5
            height = width
        }
        view.layoutParams = param
        sA.addView(view)
    }

}