package com.app.justclap.vendordetail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.justclap.R;
import com.app.justclap.adapters.AdapterAnswerList;
import com.app.justclap.asyntask.AsyncPostDataResponse;
import com.app.justclap.interfaces.ConnectionDetector;
import com.app.justclap.interfaces.ListenerPostData;
import com.app.justclap.interfaces.OnCustomItemClicListener;
import com.app.justclap.models.ModelVendorData;
import com.app.justclap.utils.AppUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewBidirectionalQuesAnswers extends AppCompatActivity implements ListenerPostData, OnCustomItemClicListener {


    TextView text_book;
    Context context;
    ConnectionDetector cd;
    LinearLayoutManager layoutManager;
    RecyclerView mRecyclerView;
    EditText text_quote;
    AdapterAnswerList adapterAnswerList;
    ModelVendorData ansList;
    RelativeLayout rl_main_layout, rl_network;
    TextView text_category_name, text_address, text_day, text_service;
    String service_id = "";
    ArrayList<ModelVendorData> questionanswer;
    public ArrayList<Object> pagerDataList;
    Toolbar toolbar;
    String date = "", userId = "", searchId = "";
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bidirectionl_ques_answers);

        context = this;
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Requirements");

        questionanswer = new ArrayList<>();
        Intent in = getIntent();
        service_id = in.getExtras().getString("service_id");
        if (in.hasExtra("user_id")) {
            userId = in.getExtras().getString("user_id");
        } else {
            userId = AppUtils.getvendorId(context);
        }
        if (in.hasExtra("searchId")) {

            searchId = in.getStringExtra("searchId");
        }
        text_service.setText(in.getExtras().getString("service_name"));
        setListener();
        hitService();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);
    }

    private void init() {
        pagerDataList = new ArrayList<>();
        overridePendingTransition(R.anim.enter,
                R.anim.exit);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        text_category_name = (TextView) findViewById(R.id.text_category_name);
        text_service = (TextView) findViewById(R.id.text_service);
        rl_main_layout = (RelativeLayout) findViewById(R.id.rl_main_layout);
        rl_network = (RelativeLayout) findViewById(R.id.rl_network);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        text_book = (TextView) findViewById(R.id.text_book);
        text_quote = (EditText) findViewById(R.id.text_quote);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        text_day = (TextView) findViewById(R.id.text_day);
        text_address = (TextView) findViewById(R.id.text_address);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }

    private void hitService() {

        try {

            // for gcm condition check
            // =================================================

            cd = new ConnectionDetector(context);

            // Check if Internet present
            if (!cd.isConnectingToInternet()) {
                //Internet Connection is not present

                rl_main_layout.setVisibility(View.GONE);
                rl_network.setVisibility(View.VISIBLE);

                //  AppUtils.showCustomAlert(ViewDetailAnswers.this, getResources().getString(R.string.message_network_problem));

                // stop executing code by return
                return;

            } else {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        2);

                nameValuePairs
                        .add(new BasicNameValuePair(
                                "serviceID", service_id));

                nameValuePairs
                        .add(new BasicNameValuePair(
                                "userID", userId));

                nameValuePairs
                        .add(new BasicNameValuePair(
                                "isUniDirectional", "2"));

                nameValuePairs
                        .add(new BasicNameValuePair(
                                "searchId", searchId));

                new AsyncPostDataResponse(ViewBidirectionalQuesAnswers.this, 1, nameValuePairs,
                        getString(R.string.url_base_new)
                                + getString(R.string.getCustomersResponse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setListener() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO bind to drawer with... injection?

                overridePendingTransition(R.anim.left_to_right,
                        R.anim.right_to_left);
                finish();
            }

        });


    }

    private ArrayList<ModelVendorData> createQuestionModerData(JSONObject jsonData) {

        try {
            int type = Integer.parseInt(jsonData.getString("questionTypeID"));

            switch (type) {

                case 1:
                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject jo1 = jsonData.getJSONObject("options");
                    ansList.setAnswer(jo1.getString("optionText"));
                    questionanswer.add(ansList);
                    break;

                case 2:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject jo2 = jsonData.getJSONObject("options");
                    ansList.setAnswer(jo2.getString("optionText"));
                    questionanswer.add(ansList);

                    break;

                case 3:
                    try {
                        ansList = new ModelVendorData();
                        ansList.setQuestion(jsonData.getString("questionText"));
                        JSONObject jloc4 = jsonData.getJSONObject("options");

                        ansList.setAnswer(jloc4.getString("optionText"));
                        questionanswer.add(ansList);
                        if (date.length() > 0) {
                            date = date + ", " + jloc4.getString("optionText");
                        } else {
                            date = jloc4.getString("optionText");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));
                    JSONObject jloc = jsonData.getJSONObject("options");

                    String lat = jloc.getString("latitude");
                    String lon = jloc.getString("longitude");
                    text_address.setText(jloc.getString("optionText"));
                    ansList.setAnswer(jloc.getString("optionText"));
                    questionanswer.add(ansList);
                    break;

                case 5:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONArray jo4 = jsonData.getJSONArray("options");
                    String s = "";
                    for (int k = 0; k < jo4.length(); k++) {

                        JSONObject j5 = jo4.getJSONObject(k);
                        if (s.length() > 0) {
                            s = s + ", " + j5.getString("optionText");
                        } else {
                            s = j5.getString("optionText");
                        }
                    }
                    ansList.setAnswer(s);
                    questionanswer.add(ansList);

                    break;

                case 6:
                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));
                    JSONObject j3 = jsonData.getJSONObject("options");
                    if (date.length() > 0) {
                        date = date + ", " + j3.getString("optionText");
                    } else {
                        date = j3.getString("optionText");
                    }
                    ansList.setAnswer(j3.getString("optionText"));
                    questionanswer.add(ansList);
                    break;

                case 7:
                    ansList = new ModelVendorData();
                    JSONObject jloc1 = jsonData.getJSONObject("options");
                    ansList.setQuestion(jsonData.getString("questionText"));
                    String lat1 = jloc1.getString("latitude");
                    String lon1 = jloc1.getString("longitude");
                    text_address.setText(jloc1.getString("city") + ", " + jloc1.getString("optionText") + ", Landmark=" + jloc1.getString("landmark"));
                    ansList.setAnswer(jloc1.getString("optionText"));
                    questionanswer.add(ansList);
                    break;

                case 8:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject j2 = jsonData.getJSONObject("options");
                    ansList.setAnswer(j2.getString("optionText"));
                    questionanswer.add(ansList);

                    break;

                case 9:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject options = jsonData.getJSONObject("options");
                    ansList.setAnswer(options.getString("optionText"));
                    questionanswer.add(ansList);

                    break;
                case 10:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject option = jsonData.getJSONObject("options");
                    ansList.setAnswer(option.getString("optionText"));
                    questionanswer.add(ansList);

                    break;

                case 11:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject option1 = jsonData.getJSONObject("options");
                    ansList.setAnswer(option1.getString("optionText"));
                    questionanswer.add(ansList);

                    break;
                case 12:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject option2 = jsonData.getJSONObject("options");
                    ansList.setAnswer(option2.getString("optionText"));
                    questionanswer.add(ansList);

                    break;

                case 14:

                    ansList = new ModelVendorData();
                    ansList.setQuestion(jsonData.getString("questionText"));

                    JSONObject option21 = jsonData.getJSONObject("options");
                    ansList.setAnswer("From : " + option21.getString("src_address") + "\nTo : " + option21.getString("dest_address") +
                            "\nDate & Time : " + option21.getString("selected_date") + ", " + option21.getString("selected_time"));
                    questionanswer.add(ansList);

                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questionanswer;

    }


    @Override
    public void onPostRequestSucess(int position, String response) {
        try {

            if (position == 1) {
                JSONObject jObject = new JSONObject(response);
                JSONObject commandResult = jObject
                        .getJSONObject("commandResult");

                if (commandResult.getString("success").equalsIgnoreCase("1")) {

                    JSONObject data = commandResult.getJSONObject("data");

                    String inf = data.getString("CustomerResponse");

                    String ret = inf.replace("\\", "");
                    JSONObject main = new JSONObject(ret);
                    JSONArray questionArray1 = main.getJSONArray("queries");
                    Log.e("questionArray1", questionArray1.toString());

                    for (int i = 0; i < questionArray1.length(); i++) {

                        JSONObject jo = questionArray1.getJSONObject(i);
                        pagerDataList.add(createQuestionModerData(jo));

                    }

                    rl_main_layout.setVisibility(View.VISIBLE);
                    rl_network.setVisibility(View.GONE);

                    adapterAnswerList = new AdapterAnswerList(context, this, questionanswer);
                    mRecyclerView.setAdapter(adapterAnswerList);
                    if (!date.equalsIgnoreCase("")) {
                        text_day.setVisibility(View.VISIBLE);
                        text_day.setText(date);
                    } else {
                        text_day.setVisibility(View.GONE);
                    }


                }
            }


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onPostRequestFailed(int position, String response) {
        AppUtils.showCustomAlert(ViewBidirectionalQuesAnswers.this, getResources().getString(R.string.problem_server));
    }

    @Override
    public void onItemClickListener(int position, int flag) {

    }
}
