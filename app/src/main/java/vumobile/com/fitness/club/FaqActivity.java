package vumobile.com.fitness.club;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class FaqActivity extends AppCompatActivity {

    private TextView txtFaqBlink, txtFaqRobi;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        initUI();


        if (SplashActivity.MSISDN.startsWith("88019")){
            txtFaqBlink.setVisibility(View.VISIBLE);
            txtFaqRobi.setVisibility(View.GONE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.other_color));
            txtFaqBlink.setText("1.What is Fitness Club WAP Portal?\n" +
                    "Ans: Fitness Club Fitness Club is a mobile fitness portal to get various health and fitness tips from celebrity and other fitness experts. This service also offers a wide range of yoga and workout videos of celebrities and other fitness experts to get inspired and follow them to be fit every day. Workout is not only helps to be fit but also it makes you feel good and gives you positive energy. This fitness service will be very effective for students, teenagers, youth and all the health conscious people around. \n" +
                    "\n" +
                    "Service Modality: \n" +
                    "● Fitness Club is a daily subscription based WAP service. \n" +
                    "● Fitness Club offers various fitness related contents such as yoga, zumba, easy workout videos, healthy diet charts etc. \n" +
                    "● Fitness Club subscription charge is TK2+(VAT, SD, SC)/day with daily auto-renewal feature. \n" +
                    "● Subscribers will get vast pool of fitness videos from celebrity to watch. Also, they will have an option to watch other fitness videos from yoga, zumba, easy workout videos etc. \n" +
                    "● Active subscribers can enjoy 5 free fitness videos daily against their subscription. \n" +
                    "● Also, active subscribers can read diet chart every day at free of cost. \n" +
                    "● If subscriber wants to enjoy more fitness videos, they can purchase additional 5-views pack by paying additional TK2+(VAT, SD, SC). Subscribers may purchase multiple packages if they want. \n" +
                    "● Daily subscription charge of TK2+(VAT, SD, SC) will be deducted from the user’s account balance (prepaid or postpaid). \n" +
                    "● Since this is a subscription model, user will be still charged if they do not avail their free content. \n" +
                    "● This cycle will continue until user deactivates the service. \n" +
                    "● Service can be deactivated anytime by sending SMS keyword: STOP FC to 26624, or by clicking cancel subscription button from portal. \n" +
                    "● User can also cancel subscription by calling our helpline numbers. \n" +
                    "2.What is the tariff plan for Fitness Club subscribers? :\n" +
                    "Ans: (Price without SD+VAT+Surcharge) \n" +
                    "Subscription fee: \n" +
                    "●2TK + (VAT, SD & SC) for Daily Auto Renew Subscription.\n" +
                    "\n" +
                    "\n" +
                    "3.\tHow to use this Fitness Club portal?\n" +
                    "Ans: User needs to log on to WAP site amarfitness.com from your mobile browser with APN Internet.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "4. How to subscribe in Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: START FC to 26624 By WAP Portal: Go to amarfitness.com and click on the SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "5.\tHow to un-subscribe from Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: Write sms STOP FC or STOP ALL to 26624 By WAP Portal: Go to amarfitness.com and click on the UN-SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "6.\tHow many content are Free for a day?\n" +
                    "Ans: Active subscribers can enjoy 5 free fitness videos daily against their subscription\n" +
                    "\n" +
                    "\n" +
                    "7. Can I use any browser to access the Fitness Club service? \n" +
                    "Ans: All.\n" +
                    "\n" +
                    "\n" +
                    "8. Standard Data Charge Applicable. \n" +
                    "\n" +
                    "\n" +
                    "9.\tDon’t find answers here?\n" +
                    "Ans: Contact Us: Helpline [08:00 AM – 07:00 PM]: Email: support@vumobile.biz .\n" +
                    "\n" +
                    "\n" +
                    "Banglalink : \n" +
                    "WhatsApp support number: 01936236705 \n" +
                    "WhatsApp support name: VU CS Banglalink\n");
        }else if (SplashActivity.MSISDN.startsWith("88018") || SplashActivity.MSISDN.startsWith("88016")){
            txtFaqRobi.setVisibility(View.VISIBLE);
            txtFaqBlink.setVisibility(View.GONE);
            txtFaqRobi.setText("1.What is Fitness Club WAP Portal?\n" +
                    "Ans: Fitness Club Fitness Club is a mobile fitness portal to get various health and fitness tips from celebrity and other fitness experts. This service also offers a wide range of yoga and workout videos of celebrities and other fitness experts to get inspired and follow them to be fit every day. Workout is not only helps to be fit but also it makes you feel good and gives you positive energy. This fitness service will be very effective for students, teenagers, youth and all the health conscious people around. \n" +
                    "\n" +
                    "2.What is the tariff plan for Fitness Club subscribers? :\n" +
                    "Ans: (Price without SD+VAT+Surcharge) \n" +
                    "Subscription fee: \n" +
                    "?2TK + (VAT, SD & SC) for Daily Auto Renew Subscription.\n" +
                    "?2TK + (VAT, SD & SC) for Daily (1 day) Subscription.\n" +
                    "?7TK + (VAT, SD & SC) for Weekly Auto Renew Subscription.\n" +
                    "?7TK + (VAT, SD & SC) for Weekly (7 days) Subscription.\n" +
                    "\n" +
                    "(Price including SD+VAT+Surcharge) \n" +
                    "Subscription fee: \n" +
                    "?2.44 TK for Daily (1 day) Auto Renew Subscription.\n" +
                    "?2.44 TK for Daily (1 day) Subscription\n" +
                    "?8.52 TK for Weekly (7 days) Auto Renew Subscription.\n" +
                    "?8.52 TK for Weekly (7 days) Subscription.\n" +
                    "\n" +
                    "3.\tHow to use this Fitness Club portal?\n" +
                    "Ans: User needs to log on to WAP site amarfitness.com from your mobile browser with Robi Internet.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "4. How to subscribe in Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: START FC/ FCW / FCN / FCWN to 26624 By WAP Portal: Go to amarfitness.com and click on the SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "5.\tHow to un-subscribe from Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: STOP FC/ FCW / FCN / FCWN / STOP ALL to 26624 By WAP Portal: Go to amarfitness.com and click on the UN-SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "6.\tHow many content are Free for a day?\n" +
                    "Ans: Unlimited Free\n" +
                    "\n" +
                    "\n" +
                    "7. Can I use any browser to access the Fitness Club service? \n" +
                    "Ans: All.\n" +
                    "\n" +
                    "\n" +
                    "8. Standard Data Charge Applicable. \n" +
                    "\n" +
                    "\n" +
                    "9.\tDon’t find answers here?\n" +
                    "Ans: Contact Us: Helpline [08:00 AM – 07:00 PM]: Email: support@vumobile.biz .\n" +
                    "\n" +
                    "Phone: 8801814426426\n" +
                    "\n" +
                    "\n" +
                    "Robi : \n" +
                    "WhatsApp support number: 01814426426 \n" +
                    "WhatsApp support name: VU CS Robi\n" +
                    "\n");
        }else {
            showAlert();
            txtFaqRobi.setVisibility(View.VISIBLE);
            txtFaqBlink.setVisibility(View.GONE);
            txtFaqRobi.setText("1.What is Fitness Club WAP Portal?\n" +
                    "Ans: Fitness Club Fitness Club is a mobile fitness portal to get various health and fitness tips from celebrity and other fitness experts. This service also offers a wide range of yoga and workout videos of celebrities and other fitness experts to get inspired and follow them to be fit every day. Workout is not only helps to be fit but also it makes you feel good and gives you positive energy. This fitness service will be very effective for students, teenagers, youth and all the health conscious people around. \n" +
                    "\n" +
                    "2.What is the tariff plan for Fitness Club subscribers? :\n" +
                    "Ans: (Price without SD+VAT+Surcharge) \n" +
                    "Subscription fee: \n" +
                    "?2TK + (VAT, SD & SC) for Daily Auto Renew Subscription.\n" +
                    "?2TK + (VAT, SD & SC) for Daily (1 day) Subscription.\n" +
                    "?7TK + (VAT, SD & SC) for Weekly Auto Renew Subscription.\n" +
                    "?7TK + (VAT, SD & SC) for Weekly (7 days) Subscription.\n" +
                    "\n" +
                    "(Price including SD+VAT+Surcharge) \n" +
                    "Subscription fee: \n" +
                    "?2.44 TK for Daily (1 day) Auto Renew Subscription.\n" +
                    "?2.44 TK for Daily (1 day) Subscription\n" +
                    "?8.52 TK for Weekly (7 days) Auto Renew Subscription.\n" +
                    "?8.52 TK for Weekly (7 days) Subscription.\n" +
                    "\n" +
                    "3.\tHow to use this Fitness Club portal?\n" +
                    "Ans: User needs to log on to WAP site amarfitness.com from your mobile browser with Robi Internet.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "4. How to subscribe in Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: START FC/ FCW / FCN / FCWN to 26624 By WAP Portal: Go to amarfitness.com and click on the SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "5.\tHow to un-subscribe from Fitness Club service?\n" +
                    "Ans: By SMS: Type and send activation keywords: STOP FC/ FCW / FCN / FCWN / STOP ALL to 26624 By WAP Portal: Go to amarfitness.com and click on the UN-SUBSCRIBE button.\n" +
                    "\n" +
                    "\n" +
                    "6.\tHow many content are Free for a day?\n" +
                    "Ans: Unlimited Free\n" +
                    "\n" +
                    "\n" +
                    "7. Can I use any browser to access the Fitness Club service? \n" +
                    "Ans: All.\n" +
                    "\n" +
                    "\n" +
                    "8. Standard Data Charge Applicable. \n" +
                    "\n" +
                    "\n" +
                    "9.\tDon’t find answers here?\n" +
                    "Ans: Contact Us: Helpline [08:00 AM – 07:00 PM]: Email: support@vumobile.biz .\n" +
                    "\n" +
                    "Phone: 8801814426426\n" +
                    "\n" +
                    "\n" +
                    "Robi : \n" +
                    "WhatsApp support number: 01814426426 \n" +
                    "WhatsApp support name: VU CS Robi\n" +
                    "\n");
        }
    }

    private void initUI() {

        txtFaqBlink = (TextView) findViewById(R.id.txtFaqBlink);
        txtFaqRobi = (TextView) findViewById(R.id.txtFaqRobi);

    }

    public void showAlert(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please use mobile data.");
        alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
