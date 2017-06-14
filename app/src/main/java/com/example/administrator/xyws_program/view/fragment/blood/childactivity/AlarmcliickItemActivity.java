package com.example.administrator.xyws_program.view.fragment.blood.childactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.xyws_program.R;
import com.example.administrator.xyws_program.base.BaseActivity;
import com.example.administrator.xyws_program.presenter.Blood.BloodAlarmclickItem_imple;
import com.example.administrator.xyws_program.view.fragment.blood.childactivity.Inter.AlarmclickItem_Inter;
import com.example.administrator.xyws_program.view.zidingyiview.WheelView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmcliickItemActivity extends BaseActivity implements AlarmclickItem_Inter {

    @BindView(R.id.alarmclock_toolbar_backimg)
    ImageView alarmclockToolbarBackimg;
    @BindView(R.id.alarmclockstorage)
    TextView alarmclockstorage;
    @BindView(R.id.information_toolbar)
    Toolbar informationToolbar;
    @BindView(R.id.alarmclock_lin2)
    RelativeLayout alarmclockLin2;
    @BindView(R.id.alarmclock_title)
    TextView alarmclockTitle;
    @BindView(R.id.alarmclock_tv)
    TextView alarmclockTv;
    @BindView(R.id.alarmclock_et1)
    EditText alarmclockEt1;
    @BindView(R.id.alarmclock_lin3)
    RelativeLayout alarmclockLin3;
    @BindView(R.id.alarmclock_tv2)
    TextView alarmclockTv2;
    @BindView(R.id.alarmclock_et2)
    EditText alarmclockEt2;
    @BindView(R.id.alarmclock_lin4)
    RelativeLayout alarmclockLin4;
    @BindView(R.id.activity_alarmcliick)
    ScrollView activityAlarmcliick;
    @BindView(R.id.alarmclock_lin11)
    LinearLayout alarmclockLin11;
    private static final ArrayList<String> HOUR=new ArrayList();
    private static final ArrayList<String> MINES=new ArrayList();
    private String resulthour=10+"";
    private String resultmines=30+"";
    private BloodAlarmclickItem_imple imple;
    private Button button;

    @Override
    protected int getLayout() {
        return R.layout.activity_alarmcliick;
    }

    @Override
    protected void init() {
        button = (Button) findViewById(R.id.alarmclock_time);
    }

    @Override
    protected void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showminnes();
            }
        });
    }

    @Override
    protected void loadData() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.alarmclock_toolbar_backimg, R.id.alarmclockstorage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alarmclock_toolbar_backimg:
                finish();
                break;
            case R.id.alarmclockstorage:
                //编辑框里面的内容

                String time=resulthour+":"+resultmines;
                String name=alarmclockEt1.getText().toString().trim();
                String number=alarmclockEt2.getText().toString().trim();
                if(!time.equals("")){
                    if(!name.equals("")){
                        if(!number.equals("")){
                            imple = new BloodAlarmclickItem_imple(this);
                            imple.updatadb(this,time,name,number);
                            finish();
                            Toast.makeText(AlarmcliickItemActivity.this,"已经设置用药提醒！",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AlarmcliickItemActivity.this,"服用剂量不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AlarmcliickItemActivity.this,"药品名称不能为空！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AlarmcliickItemActivity.this,"请选择时间不能为空！",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void getdata() {

    }
    private void showminnes() {
        for(int i=0;i<24;i++){
            HOUR.add(1+i+"");
        }
        for(int i=0;i<60;i++){
            MINES.add(1+i+"");
        }
        // 构建弹出框View
        View outerView = LayoutInflater.from(AlarmcliickItemActivity.this)
                .inflate(R.layout.updatamineswheel_view, null);

        WheelView hour = (WheelView) outerView
                .findViewById(R.id.updatwheel_hour);
        WheelView mines = (WheelView) outerView
                .findViewById(R.id.updatwheel_mines);

        hour.setOffset(2);
        mines.setOffset(2);

        hour.setItems(HOUR);// 实际内容
        mines.setItems(MINES);


        hour.setSeletion(9);// 设置默认被选中的项目
        mines.setSeletion(29);

        //滑动监听
        hour.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                resulthour = item;
            }
        });
        mines.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                resultmines=item;
            }
        });
        // 展示弹出框
        new AlertDialog.Builder(AlarmcliickItemActivity.this)
                .setTitle("设置时间").setView(outerView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     button.setText(resulthour+":"+resultmines);
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
