package file.tangqi.com.file;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static android.Manifest.permission.SEND_SMS;

public class ApplyActivity extends MainActivity {

    private TextView leader;
    private TextView member;
    private TextView reason;
    private TextView start;
    private TextView end;
    private String chaosonglingdao = "";
    private String suixingrenyuan = "";
    private String choosedialog = "";
    private String sendmsg;
    private EditText destination;
    private int startyear, endyear, year;
    private int startmonth, endmonth, month;
    private int startday, endday, day;
    private EditText comments;
    private Button sendduanxin;
    private Button sendwechat;
    private String shiyou;
    private EditText phone;
    private static final int PERMISSON_REQUESTCODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = this.getIntent();
        final String name = intent.getStringExtra("name");
        final String[] leaders = new String[]{"黄文军", "刘权"};
        final String[] reasons = new String[]{"出差", "休假", "学习", "事假", "病假", "其它"};
        final String[] members = new String[]{"黄文军", "刘权", "邱爽", "丁凯", "吴宏",
                "唐琪", "彭飞", "向东", "徐跃林", "张有龙",
                "徐军", "陈果", "郭迎春", "谢文", "侯吉忠",
                "周勇军", "尹栋", "闫守成", "李昊", "罗朝鹏",
                "凌国平", "姜和俊", "胡寿伟", "荣英佼", "朱俊"};
        final boolean[] checkItemsleader = {false, false};
        leader = (TextView) findViewById(R.id.leader);
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ApplyActivity.this).setTitle("请选择抄送的室领导")
                        .setMultiChoiceItems(leaders, checkItemsleader, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkItemsleader[which] = isChecked;
                                chaosonglingdao = "";
                                for (int i = 0; i < checkItemsleader.length; i++) {
                                    if (checkItemsleader[i]) {
                                        chaosonglingdao = chaosonglingdao + leaders[i];
//                                    Toast.makeText(ApplyActivity.this,"一"+leaders[i]+"一",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(ApplyActivity.this, "你点击了" + chaosonglingdao, Toast.LENGTH_SHORT).show();
                        leader.setText(chaosonglingdao);
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        reason = (TextView) findViewById(R.id.reasons);
        final boolean[] checkItemsreason = {false, false, false, false, false, false};
        reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ApplyActivity.this).setTitle("请选择请假事由")
                        .setSingleChoiceItems(reasons, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reason.setText(reasons[which]);
                                Toast.makeText(ApplyActivity.this, "你点击了" + reasons[which], Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                shiyou = reasons[which];
                            }
                        }).show();
            }
        });
        member = (TextView) findViewById(R.id.member);
        final boolean[] checkItemsmember = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false};
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ApplyActivity.this).setTitle("请选择随行人员")
                        .setMultiChoiceItems(members, checkItemsmember, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkItemsmember[which] = isChecked;
                                suixingrenyuan = "";
                                for (int i = 0; i < checkItemsmember.length; i++) {
                                    if (checkItemsmember[i]) {
                                        suixingrenyuan = suixingrenyuan + members[i] + " ";
                                    }
                                }
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ApplyActivity.this, "你点击了" + suixingrenyuan, Toast.LENGTH_SHORT).show();
                        member.setText(suixingrenyuan);
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        start = (TextView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance(Locale.CHINA);
                Date mydate = new Date();
                date.setTime(mydate);
                startyear = date.get(Calendar.YEAR);
                startmonth = date.get(Calendar.MONTH) + 1;
                startday = date.get(Calendar.DAY_OF_MONTH);
                choosedialog = "start";
                dialog();
            }

        });

        end = (TextView) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance(Locale.CHINA);
                Date mydate = new Date();
                date.setTime(mydate);
                endyear = date.get(Calendar.YEAR);
                endmonth = date.get(Calendar.MONTH) + 1;
                endday = date.get(Calendar.DAY_OF_MONTH);
                choosedialog = "end";
                dialog();
            }
        });

        sendduanxin = (Button) findViewById(R.id.sendduanxin);
        sendduanxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = (EditText) findViewById(R.id.destination);
                String dest = destination.getText().toString();
                comments = (EditText) findViewById(R.id.comments);
                String comm = comments.getText().toString();
                phone = (EditText) findViewById(R.id.phone);
                String phe = phone.getText().toString();
                sendmsg = "领导：" + chaosonglingdao + "\n" + "请假人：" + name + "\n" + "请假事由：" + shiyou + "\n" + "去向：" + dest + "\n" + "起始日期：" + startyear + "-" + startmonth + "-" + startday + "\n" +
                        "结束日期：" + endyear + "-" + endmonth + "-" + endday + "\n" + "随行人员：" + suixingrenyuan + "\n" + "联系电话：" + phe + "\n" + "备注：" + comm;
                ClipboardManager clip = (ClipboardManager) ApplyActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(sendmsg); // 复制
                Toast.makeText(ApplyActivity.this, chaosonglingdao, Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(ApplyActivity.this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ApplyActivity.this, new String[]{SEND_SMS},
                            PERMISSON_REQUESTCODE);//自定义的code
                }
//                if (chaosonglingdao.length() == 0) {
//                    Toast.makeText(ApplyActivity.this, "请选择抄送领导", Toast.LENGTH_SHORT).show();
//                }
                if(chaosonglingdao.equals("黄文军")){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:（+86）18915342286"));
                    intent.putExtra("sms_body", sendmsg);
                    startActivity(intent);
                }
//
               if(chaosonglingdao.equals("刘权")){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:（+86）18951561386"));
                    intent.putExtra("sms_body", sendmsg);
                    startActivity(intent);
                }
//
                if(chaosonglingdao.equals("黄文军刘权")){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:（+86）18915342286;（+86）18951561386"));
                    intent.putExtra("sms_body", sendmsg);
                    startActivity(intent);
                }

            }
        });
        sendwechat = (Button) findViewById(R.id.sendwechat);
        sendwechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = (EditText) findViewById(R.id.destination);
                String dest = destination.getText().toString();
                comments = (EditText) findViewById(R.id.comments);
                String comm = comments.getText().toString();
                phone = (EditText) findViewById(R.id.phone);
                String phe = phone.getText().toString();
                sendmsg = "领导：" + chaosonglingdao + "\n" + "请假人：" + name + "\n" + "请假事由：" + shiyou + "\n" + "去向：" + dest + "\n" + "起始日期：" + startyear + "-" + startmonth + "-" + startday + "\n" +
                        "结束日期：" + endyear + "-" + endmonth + "-" + endday + "\n" + "随行人员：" + suixingrenyuan + "\n" + "联系电话：" + phe + "\n" + "备注：" + comm;
                ClipboardManager clip = (ClipboardManager) ApplyActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(sendmsg); // 复制
                Toast.makeText(ApplyActivity.this, "复制成功，去发送", Toast.LENGTH_SHORT).show();
                   openWeChat();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            isExit.setTitle("确定退出？");
            isExit.setMessage("退出后信息将全部丢失");
            isExit.setIcon(R.mipmap.aiapps_empty_icon_error);
            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            isExit.show();
        }
        return false;
    }

    public void dialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dialogyear, int dialogmonth, int dialogdayOfMonth) {
                Toast.makeText(ApplyActivity.this, dialogyear + "-" + (dialogmonth + 1) + "-" + dialogdayOfMonth, Toast.LENGTH_SHORT).show();
                year = dialogyear;
                month = dialogmonth;
                day = dialogdayOfMonth;
                if (choosedialog == "start") {
                    start.setText(dialogyear + "-" + (dialogmonth + 1) + "-" + dialogdayOfMonth);
                    startyear = dialogyear;
                    startmonth = dialogmonth + 1;
                    startday = dialogdayOfMonth;
                }
                if (choosedialog == "end") {
                    end.setText(dialogyear + "-" + (dialogmonth + 1) + "-" + dialogdayOfMonth);
                    endyear = dialogyear;
                    endmonth = dialogmonth + 1;
                    endday = dialogdayOfMonth;
                }
            }

        };
        DatePickerDialog dialog = new DatePickerDialog(this, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, dateSetListener, startyear, startmonth - 1, startday);
        dialog.setTitle("请选择日期");
        dialog.show();
    }

    private void openWeChat() {
        if(isWeixinAvilible(ApplyActivity.this)) {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        }
        else{
            Toast.makeText(ApplyActivity.this,"检测到您未安装微信！",Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);//可在此继续其他操作
        ;//可在此继续其他操作。
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            }
        }
    }
}
