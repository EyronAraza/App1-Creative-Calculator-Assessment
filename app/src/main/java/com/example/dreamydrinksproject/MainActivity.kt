package com.example.dreamydrinksproject

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION", "SameParameterValue")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show and hide views from the start
        displayViews(arrayOf(btn_start, txt_welcome, img_shaker), true)
        displayViews(arrayOf(img_shakerBottle, txt_foundation, circle_coffee, circle_juice,circle_tea, img_coffee,
        img_juice, img_tea, txt_drink_name, btn_next, img_coffee_pour, txt_flavor, circle_one, circle_two,
        circle_three, img_milk, img_caramel, img_cocoa, img_mint, img_lemon, txt_flavor_name, btn_mix,
        radio_flavors, img_milk_pour, txt_you_got, txt_result_drink, txt_result_desc, btn_new_drink, img_cloud), false)

        // Show Pop-Up Window at start
        myPopupView()

        // 'Get Started' Button
        btn_start.setOnClickListener{
            // Play fade out animation
            playAnimation(arrayOf(btn_start, txt_welcome), 2)

            // Go to 'Select Foundation' section
            foundationSelect()
        }

        // 'Info' Button for opening Pop-Up Info
        img_info.setOnClickListener{
            myPopupView()
        }
    }

    @SuppressLint("InflateParams")
    private fun myPopupView(){
        // Referencing pop-up window
        val popupBinding = layoutInflater.inflate(R.layout.popup_window, null)

        // Make pop-window as Dialog
        val myPopup = Dialog(this)
        myPopup.setContentView(popupBinding)

        // Display pop-up window
        myPopup.setCancelable(true)
        myPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myPopup.show()

        // Close pop-up window by clicking anywhere
        popupBinding.setOnClickListener {
            myPopup.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun foundationSelect(){
        // Show and hide certain views for this section
        displayViews(arrayOf(img_shakerBottle, txt_foundation, circle_coffee, circle_juice, circle_tea,
        img_coffee, img_juice, img_tea, txt_drink_name), true)
        displayViews(arrayOf(btn_start, txt_welcome,img_shaker), false)

        // Uncheck Radio Buttons and remove highlight selections
        radio_foundation.clearCheck()
        for (radios in arrayOf(radio_coffee, radio_tea, radio_juice)){
            if (!radios.isChecked){
                txt_drink_name.text = "..."
                for (circle_icons in arrayOf(circle_coffee, circle_tea, circle_juice)){
                    circle_icons.setBackgroundResource(R.drawable.circle)
                }
            }
        }

        // Play drop animation of bottle shaker
        playAnimation(arrayOf(img_shakerBottle), 3)
        // Play sound effect "shaker drop"
        playSound(5)

        // Play fade-in animation
        playAnimation(arrayOf(txt_foundation, circle_coffee, circle_juice,circle_tea), 1)

        // Select 'Coffee'
        circle_coffee.setOnClickListener{
            // Radio button check
            radio_coffee.isChecked = true

            // Display drink name and highlight its selection
            txt_drink_name.text = "> COFFEE <"
            if (radio_coffee.isChecked){
                // Call highlightSelection() function
                highlightSelection(circle_coffee,
                arrayOf(circle_tea, circle_juice),
                btn_next)
            }
        }

        // Select 'Tea'
        circle_tea.setOnClickListener{
            // Radio button check
            radio_tea.isChecked = true

            // Display drink name and highlight its selection
            txt_drink_name.text = "> TEA <"
            if (radio_tea.isChecked){
                // Call highlightSelection() function
                highlightSelection(circle_tea,
                arrayOf(circle_coffee, circle_juice),
                btn_next)
            }
        }

        // Select 'Juice'
        circle_juice.setOnClickListener{
            // Radio button check
            radio_juice.isChecked = true

            // Display drink name and highlight its selection
            txt_drink_name.text = "> JUICE <"
            if (radio_juice.isChecked){
                // Call highlightSelection() function
                highlightSelection(circle_juice,
                arrayOf(circle_tea, circle_coffee),
                btn_next)
            }
        }

        // 'Next' Button
        btn_next.setOnClickListener{
            // Play fade out animation
            playAnimation(arrayOf(txt_foundation, circle_coffee, circle_juice, circle_tea,
                img_coffee, img_juice, img_tea, txt_drink_name, btn_next), 2)

            // Go to 'Flavor Selection' section
            flavorSelection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun flavorSelection(){
        // Hide certain views for this section
        displayViews(arrayOf(txt_foundation, circle_coffee, circle_juice, circle_tea,
            img_coffee, img_juice, img_tea, txt_drink_name, btn_next), false)

        // Display a drink pouring on the shaker depending on the selection of the drink
        if (radio_coffee.isChecked){
            pourAnimation(img_coffee_pour)
        } else if (radio_tea.isChecked){
            pourAnimation(img_tea_pour)
        } else if (radio_juice.isChecked){
            pourAnimation(img_juice_pour)
        }

        // Play sound effect "pouring"
        playSound(3)

        // Wait for some seconds to display the flavor selection
        Handler().postDelayed({
            // Show certain views
            displayViews(arrayOf(txt_flavor, circle_one, circle_two, circle_three, txt_flavor_name), true)

            // Play fade-in animation
            playAnimation(arrayOf(txt_flavor, circle_one, circle_two, circle_three, txt_flavor_name), 1)

            // Uncheck Radio Buttons and remove highlight selections
            radio_flavors.clearCheck()
            for (radios in arrayOf(radio_milk, radio_caramel, radio_cocoa, radio_mint, radio_lemon)){
                if (!radios.isChecked){
                    txt_flavor_name.text = "..."
                    for (circle_icons in arrayOf(circle_one, circle_two, circle_three)){
                        circle_icons.setBackgroundResource(R.drawable.circle)
                    }
                }
            }

            // Show Coffee flavors
            if (radio_coffee.isChecked){
                displayViews(arrayOf(img_milk, img_caramel, img_cocoa), true)
                playAnimation(arrayOf(img_milk, img_caramel, img_cocoa), 1)

                // Select 'Milk'
                img_milk.setOnClickListener{
                    // Radio button check
                    radio_milk.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> MILK <"
                    if (radio_milk.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_two,
                            arrayOf(circle_one, circle_three),
                            btn_mix)
                    }
                }

                // Select 'Caramel'
                img_caramel.setOnClickListener{
                    // Radio button check
                    radio_caramel.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> CARAMEL <"
                    if (radio_caramel.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_one,
                            arrayOf(circle_two, circle_three),
                            btn_mix)
                    }
                }

                // Select 'Cocoa Paste'
                img_cocoa.setOnClickListener{
                    // Radio button check
                    radio_cocoa.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> COCOA PASTE <"
                    if (radio_cocoa.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_three,
                            arrayOf(circle_one, circle_two),
                            btn_mix)
                    }
                }
            } else { // Show Non-Coffee flavors
                displayViews(arrayOf(img_milk, img_mint, img_lemon), true)
                playAnimation(arrayOf(img_milk, img_mint, img_lemon), 1)

                // Select 'Milk'
                img_milk.setOnClickListener{
                    // Radio button check
                    radio_milk.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> MILK <"
                    if (radio_milk.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_two,
                            arrayOf(circle_one, circle_three),
                            btn_mix)
                    }
                }

                // Select 'Mint'
                img_mint.setOnClickListener{
                    // Radio button check
                    radio_mint.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> MINT <"
                    if (radio_mint.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_one,
                            arrayOf(circle_two, circle_three),
                            btn_mix)
                    }
                }

                // Select 'Lemon'
                img_lemon.setOnClickListener{
                    // Radio button check
                    radio_lemon.isChecked = true

                    // Display flavor name and highlight its selection
                    txt_flavor_name.text = "> LEMON <"
                    if (radio_lemon.isChecked){
                        // call highlightSelection() function
                        highlightSelection(circle_three,
                            arrayOf(circle_one, circle_two),
                            btn_mix)
                    }
                }
            }
        }, 1500)

        // 'Mix' Button
        btn_mix.setOnClickListener{
            // Play fade out animation
            playAnimation(arrayOf(txt_flavor, circle_one, circle_two, circle_three, txt_flavor_name, btn_mix), 2)
            if (radio_coffee.isChecked){
                playAnimation(arrayOf(img_milk, img_caramel, img_cocoa), 2)
            } else {
                playAnimation(arrayOf(img_milk, img_mint, img_lemon), 2)
            }

            // Go to 'Result' section
            result()
        }
    }

    private fun result(){
        // Hide certain views for this section
        displayViews(arrayOf(txt_flavor, circle_one, circle_two, circle_three, img_milk, img_caramel,
            img_cocoa, img_mint, img_lemon, txt_flavor_name, btn_mix), false)

        // Playing animations and sound effects of adding flavors to the shaker
        if (radio_milk.isChecked){
            // Play sound effect "pouring"
            playSound(3)
            pourAnimation(img_milk_pour)
        } else if (radio_caramel.isChecked){
            // Play sound effect "pop1"
            playSound(1)
            displayViews(arrayOf(img_caramel_drop), true)
            playAnimation(arrayOf(img_caramel_drop), 4)
        } else if (radio_cocoa.isChecked){
            // Play sound effect "pop1"
            playSound(1)
            displayViews(arrayOf(img_cocoa_drop), true)
            playAnimation(arrayOf(img_cocoa_drop), 4)
        } else if (radio_mint.isChecked){
            // Play sound effect "pop1"
            playSound(1)
            displayViews(arrayOf(img_mint_drop), true)
            playAnimation(arrayOf(img_mint_drop), 4)
        } else if (radio_lemon.isChecked){
            // Play sound effect "pop1"
            playSound(1)
            displayViews(arrayOf(img_lemon_drop), true)
            playAnimation(arrayOf(img_lemon_drop), 4)
        }

        // Wait for some seconds to play Shaker animation
        Handler().postDelayed({
            // Show and hide certain views
            displayViews(arrayOf(img_shaker, txt_shaking), true)
            displayViews(arrayOf(img_caramel_drop, img_cocoa_drop, img_mint_drop, img_lemon_drop, img_shakerBottle), false)

            // Play animation of shaker
            playAnimation(arrayOf(img_shaker), 5)

            // Play sound effect "shaking"
            playSound(6)
        }, 2000)

        // Wait for some seconds to play pop-up cloud animation
        Handler().postDelayed({
            // Show and hide certain views
            displayViews(arrayOf(img_cloud, img_shakerBottle), true)
            displayViews(arrayOf(img_shaker, txt_shaking), false)
            img_shaker.clearAnimation() // stop shake animation

            // Play sound effect "shaking"
            playSound(7)

            // Play Pop-Up animation
            playAnimation(arrayOf(img_cloud), 6)
        }, 5000)

        // Wait for some seconds to display result
        Handler().postDelayed({
            // Show and hide certain views
            displayViews(arrayOf(txt_you_got, txt_result_drink, txt_result_desc, btn_new_drink), true)

            // Play sound effect "pop2"
            playSound(2)

            // Display result of the drink
            resultDrinks()

            // 'MAKE NEW DRINK' Button
            btn_new_drink.setOnClickListener{
                // To go back to start screen
                makeNewDrink()
            }
        }, 6000)
    }

    private fun resultDrinks(){
        // Coffee: Golden Eden
        if (radio_coffee.isChecked && radio_milk.isChecked){
            // Call displayDrink() Function
            displayDrink(img_golden_eden,
            "GOLDEN EDEN",
            "#FFD700",
                35f,
            "This drink uses coffee as a base before milk and milk foam are added in. The dense yet delicate taste, is hidden under a golden peel.")
        }

        // Coffee: Caramel Pine-cone
        if (radio_coffee.isChecked && radio_caramel.isChecked){
            displayDrink(img_caramel_pine,
            "CARAMEL PINECONE",
            "#C68E17",
                30f,
            "Coffee with some caramel drizzled over top. The initial sweetness progresses with every layer of the drink, becoming more and more intense.")
        }

        // Coffee: Moonlit Alley
        if (radio_coffee.isChecked && radio_cocoa.isChecked){
            displayDrink(img_moonlit,
                "MOONLIT ALLEY",
                "#800000",
                35f,
                "Mix cocoa paste evenly into the coffee. Akin to stepping into a dark, narrow, and mysterious alley, only to have an unexpected meeting with sweet temptation.")
        }

        // Tea: Bright Crown
        if (radio_tea.isChecked && radio_milk.isChecked){
            displayDrink(img_bright_crown,
                "BRIGHT CROWN",
                "#FFB449",
                35f,
                "Light and fluffy milk foam is slowly injected atop milk tea, as if giving it a lovely and pure white ceremonial hat.")
        }

        // Tea: Green Tea
        if (radio_tea.isChecked && radio_mint.isChecked){
            displayDrink(img_green_tea,
                "GREEN TEA",
                "#96B83D",
                35f,
                "Mint and tea are infused together and filtered. The refreshing flavor can range from grassy-like and toasted to vegetal, sweet and seaweed-like.")
        }

        // Tea: Tart Brilliance
        if (radio_tea.isChecked && radio_lemon.isChecked){
            displayDrink(img_tart,
                "TART BRILLIANCE",
                "#FFF44F",
                35f,
                "Lemon juice is squeezed into tea with a slice of lemon slotted onto the lip of the cup as an ornament. The refreshing sweet and sour taste really gets you going.")
        }

        // Juice: Sweet Cider Lake
        if (radio_juice.isChecked && radio_milk.isChecked){
            displayDrink(img_sweet_cider,
                "SWEET CIDER LAKE",
                "#F9E076",
                30f,
                "Milk is added to fruit juice in many tiny batches up to a certain ratio. The sweet juice and the smooth mouthfeel are a fine match for one another indeed.")
        }

        // Juice: Dawning Dew
        if (radio_juice.isChecked && radio_mint.isChecked){
            displayDrink(img_dawning,
                "DAWNING DEW",
                "#74B72E",
                35f,
                "Juice and Mint together... A perfect combination! The effervescence compliments the cool, refreshing flavor. Wonderful!")
        }

        // Juice: Lemon Juice
        if (radio_juice.isChecked && radio_lemon.isChecked){
            displayDrink(img_lemon_juice,
                "LEMON JUICE",
                "#FBE790",
                35f,
                "A few squeezes of lemon juice go into the fruit juice before it is mixed well. This helps to take the edge off the sweetness and also titillate the taste buds. The ideal pre-meal drink indeed.")
        }
    }

    private fun makeNewDrink(){
        // Show and hide views from the start
        displayViews(arrayOf(btn_start, txt_welcome, img_shaker), true)
        displayViews(arrayOf(txt_you_got, txt_result_drink, txt_result_desc, btn_new_drink, img_cloud,
        img_shakerBottle, img_golden_eden, img_caramel_pine, img_moonlit, img_bright_crown, img_green_tea,
        img_tart, img_sweet_cider, img_dawning, img_lemon_juice), false)

        // Reset all Radio buttons
        radio_foundation.clearCheck()
        radio_flavors.clearCheck()

        // 'Get Started' Button
        btn_start.setOnClickListener{
            // Play fade out animation
            playAnimation(arrayOf(btn_start, txt_welcome), 2)

            // Go to 'Select Foundation' section
            foundationSelect()
        }
    }

    // Function responsible for showing or hiding an array of views
    private fun displayViews(storeViews: Array<View>, visibility: Boolean) {
        // Show views
        if (visibility) {
            for (views in storeViews) {
                views.visibility = View.VISIBLE
            }
        }
        // Hide views
        else {
            for (views in storeViews){
                views.visibility = View.GONE
            }
        }
    }

    // Function responsible for displaying the result of the drink
    private fun displayDrink(drink_img: View, drink_name: String, text_color: String, text_size: Float, drink_desc: String){
        drink_img.visibility = View.VISIBLE
        txt_result_drink.text = drink_name
        txt_result_drink.setTextColor(Color.parseColor(text_color))
        txt_result_drink.textSize = text_size
        txt_result_desc.text = drink_desc
    }

    // Function responsible for displaying pour animation
    private fun pourAnimation(storeView: View){
        // Load animations
        val animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Display pouring
        storeView.visibility = View.VISIBLE
        storeView.startAnimation(animFadeIn)

        // Wait for some seconds
        Handler().postDelayed({
            // Hide view of pouring
            storeView.startAnimation(animFadeOut)
            storeView.visibility = View.GONE
        }, 1500)
    }

    // Function responsible for playing animations
    private fun playAnimation(storeViews: Array<View>, animSelection: Int){
        // Load animations
        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animDrop = AnimationUtils.loadAnimation(this, R.anim.drop)
        val animDrop2 = AnimationUtils.loadAnimation(this, R.anim.drop2)
        val animShake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val animPopUp = AnimationUtils.loadAnimation(this, R.anim.pop_up)

        // Select animation by passing int value
        when(animSelection){
            // Clear animation
            0 -> {
                for (views in storeViews){
                    views.clearAnimation()
                }
            }
            // Fade-In animation
            1 -> {
                for (views in storeViews){
                    views.startAnimation(animFadeIn)
                }
            }
            // Fade-Out animation
            2 -> {
                for (views in storeViews){
                    views.startAnimation(animFadeOut)
                }
            }
            // Drop animation
            3 -> {
                for (views in storeViews){
                    views.startAnimation(animDrop)
                }
            }
            // Drop animation 2
            4 -> {
                for (views in storeViews){
                    views.startAnimation(animDrop2)
                }
            }
            // Shake animation
            5 -> {
                for (views in storeViews){
                    views.startAnimation(animShake)
                }
            }
            // Pop-Up animation
            6 -> {
                for (views in storeViews){
                    views.startAnimation(animPopUp)
                }
            }
        }
    }

    // Function responsible for playing sound effect
    private fun playSound(soundSelect: Int){
        // Create MediaPlayer
        val mp = MediaPlayer()

        // Select sound effect by passing int value
        when(soundSelect){
            1 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.pop1))
                mp.prepare()
                mp.start()
            }
            2 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.pop2))
                mp.prepare()
                mp.start()
            }
            3 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.pouring))
                mp.prepare()
                mp.start()
            }
            4 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.selection))
                mp.prepare()
                mp.start()
            }
            5 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.shakerdrop))
                mp.prepare()
                mp.start()
            }
            6 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.shaking))
                mp.prepare()
                mp.start()
            }
            7 -> {
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.harp))
                mp.prepare()
                mp.start()
            }
        }
    }

    // Function responsible for highlight selection
    private fun highlightSelection(highlightView: View, unHighlightView: Array<View>, button: View){
        // Play sound effect "pop1"
        playSound(4)

        // Highlight a selection
        highlightView.setBackgroundResource(R.drawable.circle_green)

        // Unhighlight any other selections
        for (views in unHighlightView){
            views.setBackgroundResource(R.drawable.circle)
        }

        // Display Button
        button.visibility = View.VISIBLE
    }
}