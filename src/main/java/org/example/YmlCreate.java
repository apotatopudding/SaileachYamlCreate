package org.example;

/**
 * @author apotatopudding
 * @date 2022/12/14 11:41
 */

import org.springframework.scheduling.support.CronExpression;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Objects;

public class YmlCreate {

    JFrame frame;

    YmlInfo ymlInfo = new YmlInfo();

    CronCreate cronCreate = new CronCreate();

    JLabel show = new JLabel();{
        show.setText("端口号：8088");
        show.setBounds(100, 200, 400, 420);
    }

    Font font = new Font("宋体", Font.BOLD,15);

    String JLabelHead = "<html>";
    String JLabelTail = "</html>";

    public static void main(String[] args) {
        YmlCreate ymlCreate = new YmlCreate();
        ymlCreate.select();
    }

    private void select(){

        frame = new JFrame("信息选择与填写工具");
        frame.setBounds(100, 100, 520, 670);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        //端口信息填写
        JButton btn_Button_post = new JButton("端口填写");
        btn_Button_post.setBounds(40, 30, 200, 23);
        frame.getContentPane().add(btn_Button_post);
        btn_Button_post.addActionListener(e -> {
            frame.setVisible(false);
            post();
        });

        //bot信息填写
        JButton btn_Button_userConfig = new JButton("bot配置填写");
        btn_Button_userConfig.setBounds(270, 30, 200, 23);
        frame.getContentPane().add(btn_Button_userConfig);
        btn_Button_userConfig.addActionListener(e -> {
            frame.setVisible(false);
            userConfig();
        });

        //用户信息填写
        JButton btn_Button_userInfo = new JButton("用户配置填写");
        btn_Button_userInfo.setBounds(40, 60, 200, 23);
        frame.getContentPane().add(btn_Button_userInfo);
        btn_Button_userInfo.addActionListener(e -> {
            frame.setVisible(false);
            userInfo();
        });

        //预定时间填写
        JButton btn_Button_scheduled = new JButton("预定时间填写");
        btn_Button_scheduled.setBounds(270, 60, 200, 23);
        frame.getContentPane().add(btn_Button_scheduled);
        btn_Button_scheduled.addActionListener(e -> {
            frame.setVisible(false);
            scheduled();
        });

        //百度信息填写
        JButton btn_Button_baiduConfig = new JButton("百度配置填写");
        btn_Button_baiduConfig.setBounds(40, 90, 200, 23);
        frame.getContentPane().add(btn_Button_baiduConfig);
        btn_Button_baiduConfig.addActionListener(e -> {
            frame.setVisible(false);
            baiduConfig();
        });

        //API信息填写
        JButton btn_Button_APIConfig = new JButton("API配置填写");
        btn_Button_APIConfig.setBounds(270, 90, 200, 23);
        frame.getContentPane().add(btn_Button_APIConfig);
        btn_Button_APIConfig.addActionListener(e -> {
            frame.setVisible(false);
            API();
        });

        //百度信息填写
        JButton btn_Button_read = new JButton("读入");
        btn_Button_read.setBounds(40, 120, 60, 23);
        frame.getContentPane().add(btn_Button_read);
        btn_Button_read.addActionListener(e -> {
            frame.setVisible(false);
            read();
        });

        //生成YAML文件
        JButton btn_Button_create = new JButton("生成");
        Font postFont = new Font("宋体", Font.BOLD,25);
        btn_Button_create.setFont(postFont);
        btn_Button_create.setBounds(200, 130, 100, 35);
        frame.getContentPane().add(btn_Button_create);
        btn_Button_create.addActionListener(e -> {
            try {
                finish();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null,"生成成功");
        });

        //展示已经收录的信息
        JLabel title = new JLabel("已获取数据:");
        title.setBounds(210, 170, 100, 20);
        title.setFont(font);
        frame.getContentPane().add(title);
        update();
        frame.getContentPane().add(show);

        frame.setVisible(true);
    }

    private void post(){
        JFrame JF = new JFrame("端口填写");
        JF.setBounds(100, 100, 250, 140);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel post = new JLabel("端口号:");
        post.setFont(font);
        post.setBounds(20, 20, 100, 20);
        JF.getContentPane().add(post);

        JTextField postID = new JTextField();
        postID.setColumns(10);
        postID.setBounds(80, 20, 138, 20);
        JF.getContentPane().add(postID);

        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(40, 55, 70, 20);
        btn_Button_pass.addActionListener(e1 -> {
            JF.setVisible(false);
            if (postID.getText().matches("[0-9]+")){
                ymlInfo.setPost(Integer.valueOf(postID.getText()));
                update();
                frame.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(null,"端口号必须为纯数字","输入错误",JOptionPane.ERROR_MESSAGE);
                JF.setVisible(true);
            }
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(130, 55, 70, 20);
        btn_Button_back.addActionListener(e1 -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void userConfig(){
        JFrame JF = new JFrame("bot配置填写");
        JF.setBounds(100, 100, 280, 260);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel postRemind = new JLabel("多个字段请用空格空开");
        postRemind.setFont(font);
        postRemind.setForeground(Color.red);
        postRemind.setBounds(20, 10, 300, 20);
        JF.getContentPane().add(postRemind);
        //bot名字填写
        JLabel botName = new JLabel("bot名字:");
        botName.setFont(font);
        botName.setBounds(20, 40, 100, 20);
        JF.getContentPane().add(botName);

        JTextField botNameIn = new JTextField();
        botNameIn.setColumns(10);
        botNameIn.setBounds(100, 40, 138, 20);
        JF.getContentPane().add(botNameIn);

        //bot登录QQ填写
        JLabel qqList = new JLabel("QQ号:");
        qqList.setFont(font);
        qqList.setBounds(20, 70, 100, 20);
        JF.getContentPane().add(qqList);

        JTextField qqListIn = new JTextField();
        qqListIn.setColumns(50);
        qqListIn.setBounds(100, 70, 138, 20);
        JF.getContentPane().add(qqListIn);

        //bot登录密码填写
        JLabel pwList = new JLabel("密码:");
        pwList.setFont(font);
        pwList.setBounds(20, 100, 100, 20);
        JF.getContentPane().add(pwList);

        JTextField pwListIn = new JTextField();
        pwListIn.setColumns(50);
        pwListIn.setBounds(100, 100, 138, 20);
        JF.getContentPane().add(pwListIn);

        //bot登录协议填写
        JLabel typeList = new JLabel("登录协议:");
        typeList.setFont(font);
        typeList.setBounds(20, 130, 100, 20);
        JF.getContentPane().add(typeList);

        JComboBox<String> typeListIn = new JComboBox<>(){{
                addItem("IPAD");
                addItem("ANDROID_PAD");
                addItem("ANDROID_PHONE");
                addItem("ANDROID_WATCH");
                addItem("MACOS");
        }};
        typeListIn.setBounds(100, 130, 138, 20);
        JF.getContentPane().add(typeListIn);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 170, 70, 23);
        btn_Button_pass.addActionListener(e -> {
            ymlInfo.setBotName(botNameIn.getText());
            ymlInfo.setQqList(qqListIn.getText());
            ymlInfo.setPwList(pwListIn.getText());
            ymlInfo.setTypeList((String) typeListIn.getSelectedItem());
            update();
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 170, 70, 23);
        btn_Button_back.addActionListener(e -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void userInfo(){
        JFrame JF = new JFrame("用户配置填写");
        JF.setBounds(100, 100, 500, 250);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel postRemind = new JLabel("多个字段请用空格空开");
        postRemind.setFont(font);
        postRemind.setForeground(Color.red);
        postRemind.setBounds(10, 10, 300, 20);
        JF.getContentPane().add(postRemind);

        JLabel ownerQQ = new JLabel("号主账号:");
        ownerQQ.setFont(font);
        ownerQQ.setBounds(10, 40, 100, 20);
        JF.getContentPane().add(ownerQQ);

        JTextField ownerQQIn = new JTextField();
        ownerQQIn.setColumns(50);
        ownerQQIn.setBounds(90, 40, 138, 20);
        JF.getContentPane().add(ownerQQIn);

        JLabel adminQQ = new JLabel("管理账号:");
        adminQQ.setFont(font);
        adminQQ.setBounds(10, 70, 100, 20);
        JF.getContentPane().add(adminQQ);

        JTextField adminQQIn = new JTextField();
        adminQQIn.setColumns(50);
        adminQQIn.setBounds(90, 70, 138, 20);
        JF.getContentPane().add(adminQQIn);

        JLabel testGroup = new JLabel("测试群号:");
        testGroup.setFont(font);
        testGroup.setBounds(10, 100, 100, 20);
        JF.getContentPane().add(testGroup);

        JTextField testGroupIn = new JTextField();
        testGroupIn.setColumns(50);
        testGroupIn.setBounds(90, 100, 138, 20);
        JF.getContentPane().add(testGroupIn);

        JLabel remind = new JLabel();
        String text = "号主账号用于给每日看世界和反馈提供支持，不需要可以不填，号主只支持一位<br/>" +
                "<br/>" +
                "管理账号可以获得最高管理权限，没有可以不填，号主自动获得最高管理权限，不用重复添加<br/>" +
                "<br/>" +
                "测试群号可以排除少人退群触发<br/>";
        remind.setText(JLabelHead+text+JLabelTail);
        remind.setFont(font);
        remind.setForeground(Color.red);
        remind.setBounds(240, 0, 240, 200);
        JF.getContentPane().add(remind);

        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 140, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            ymlInfo.setOwnerQQ(ownerQQIn.getText());
            ymlInfo.setAdminQQ(adminQQIn.getText());
            ymlInfo.setTestGroup(testGroupIn.getText());
            update();
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 140, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void scheduled(){
        JFrame JF = new JFrame("预定时间填写");
        JF.setBounds(100, 100, 560, 380);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel biliJob = new JLabel("B站动态监听:");
        biliJob.setFont(font);
        biliJob.setBounds(20, 20, 150, 20);
        JF.getContentPane().add(biliJob);

        JTextField biliJobIn = new JTextField("0 */5 * * * ?");
        biliJobIn.setColumns(50);
        biliJobIn.setBounds(145, 20, 80, 20);
        JF.getContentPane().add(biliJobIn);

        JButton biliJobInsert = new JButton();
        biliJobInsert.setText("置入");
        biliJobInsert.setBounds(230,20,60,20);
        biliJobInsert.addActionListener(e -> biliJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(biliJobInsert);

        JLabel birthdayJob = new JLabel("干员庆生发送:");
        birthdayJob.setFont(font);
        birthdayJob.setBounds(20, 50, 150, 20);
        JF.getContentPane().add(birthdayJob);

        JTextField birthdayJobIn = new JTextField("0 0 8 */1 * ?");
        birthdayJobIn.setColumns(50);
        birthdayJobIn.setBounds(145, 50, 80, 20);
        JF.getContentPane().add(birthdayJobIn);

        JButton birthdayJobInsert = new JButton();
        birthdayJobInsert.setText("置入");
        birthdayJobInsert.setBounds(230,50,60,20);
        birthdayJobInsert.addActionListener(e -> birthdayJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(birthdayJobInsert);

        JLabel lookWorldJob = new JLabel("60秒看世界发送:");
        lookWorldJob.setFont(font);
        lookWorldJob.setBounds(20, 80, 150, 20);
        JF.getContentPane().add(lookWorldJob);

        JTextField lookWorldJobIn = new JTextField("0 30 7 */1 * ?");
        lookWorldJobIn.setColumns(50);
        lookWorldJobIn.setBounds(145, 80, 80, 20);
        JF.getContentPane().add(lookWorldJobIn);

        JButton lookWorldJobInsert = new JButton();
        lookWorldJobInsert.setText("置入");
        lookWorldJobInsert.setBounds(230,80,60,20);
        lookWorldJobInsert.addActionListener(e -> lookWorldJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(lookWorldJobInsert);

        JLabel updateJob = new JLabel("数据更新监听:");
        updateJob.setFont(font);
        updateJob.setBounds(20, 110, 150, 20);
        JF.getContentPane().add(updateJob);

        JTextField updateJobIn = new JTextField("0 */10 * * * ?");
        updateJobIn.setColumns(50);
        updateJobIn.setBounds(145, 110, 80, 20);
        JF.getContentPane().add(updateJobIn);

        JButton updateJobInsert = new JButton();
        updateJobInsert.setText("置入");
        updateJobInsert.setBounds(230,110,60,20);
        updateJobInsert.addActionListener(e -> updateJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(updateJobInsert);

        JLabel exterminateJob = new JLabel("剿灭提醒发送:");
        exterminateJob.setFont(font);
        exterminateJob.setBounds(20, 140, 150, 20);
        JF.getContentPane().add(exterminateJob);

        JTextField exterminateJobIn = new JTextField("0 0 17 ? * 7");
        exterminateJobIn.setColumns(50);
        exterminateJobIn.setBounds(145, 140, 80, 20);
        JF.getContentPane().add(exterminateJobIn);

        JButton exterminateJobInsert = new JButton();
        exterminateJobInsert.setText("置入");
        exterminateJobInsert.setBounds(230,140,60,20);
        exterminateJobInsert.addActionListener(e -> exterminateJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(exterminateJobInsert);

        JLabel cleanJob = new JLabel("抽卡次数更新:");
        cleanJob.setFont(font);
        cleanJob.setBounds(20, 170, 150, 20);
        JF.getContentPane().add(cleanJob);

        JTextField cleanJobIn = new JTextField("0 0 4 */1 * ?");
        cleanJobIn.setColumns(50);
        cleanJobIn.setBounds(145, 170, 80, 20);
        JF.getContentPane().add(cleanJobIn);

        JButton cleanJobInsert = new JButton();
        cleanJobInsert.setText("置入");
        cleanJobInsert.setBounds(230,170,60,20);
        cleanJobInsert.addActionListener(e -> cleanJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(cleanJobInsert);

        JLabel dayJob = new JLabel("每日任务定时:");
        dayJob.setFont(font);
        dayJob.setBounds(20, 200, 150, 20);
        JF.getContentPane().add(dayJob);

        JTextField dayJobIn = new JTextField("0 0 0 */1 * ?");
        dayJobIn.setColumns(50);
        dayJobIn.setBounds(145, 200, 80, 20);
        JF.getContentPane().add(dayJobIn);

        JButton dayJobInsert = new JButton();
        dayJobInsert.setText("置入");
        dayJobInsert.setBounds(230,200,60,20);
        dayJobInsert.addActionListener(e -> dayJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(dayJobInsert);

        JLabel picJob = new JLabel("60秒看世界获取:");
        picJob.setFont(font);
        picJob.setBounds(20, 230, 150, 20);
        JF.getContentPane().add(picJob);

        JTextField picJobIn = new JTextField("0 0 1 */1 * ?");
        picJobIn.setColumns(50);
        picJobIn.setBounds(145, 230, 80, 20);
        JF.getContentPane().add(picJobIn);

        JButton picJobInsert = new JButton();
        picJobInsert.setText("置入");
        picJobInsert.setBounds(230,230,60,20);
        picJobInsert.addActionListener(e -> picJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(picJobInsert);

        JLabel monthJob = new JLabel("每月任务刷新:");
        monthJob.setFont(font);
        monthJob.setBounds(20, 260, 150, 20);
        JF.getContentPane().add(monthJob);

        JTextField monthJobIn = new JTextField("0 0 0 1 * ?");
        monthJobIn.setColumns(50);
        monthJobIn.setBounds(145, 260, 80, 20);
        JF.getContentPane().add(monthJobIn);

        JButton monthJobInsert = new JButton();
        monthJobInsert.setText("置入");
        monthJobInsert.setBounds(230,260,60,20);
        monthJobInsert.addActionListener(e -> monthJobIn.setText(cronCreate.createSimulation.getText()));
        JF.getContentPane().add(monthJobInsert);

        JLabel remind = new JLabel("cron生成器");
        remind.setFont(font);
        remind.setForeground(Color.red);
        remind.setBounds(300, 10, 80, 20);
        JF.getContentPane().add(remind);

        JButton second = new JButton();
        second.setText("秒");
        second.setBounds(300,30,60,20);
        second.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.second();
        });
        JF.getContentPane().add(second);

        JButton minute = new JButton();
        minute.setText("分钟");
        minute.setBounds(370,30,60,20);
        minute.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.minute();
        });
        JF.getContentPane().add(minute);

        JButton hour = new JButton();
        hour.setText("小时");
        hour.setBounds(440,30,60,20);
        hour.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.hour();
        });
        JF.getContentPane().add(hour);

        JButton day = new JButton();
        day.setText("日");
        day.setBounds(300,60,60,20);
        day.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.day();
        });
        JF.getContentPane().add(day);

        JButton mouth = new JButton();
        mouth.setText("月");
        mouth.setBounds(370,60,60,20);
        mouth.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.mouth();
        });
        JF.getContentPane().add(mouth);

        JButton week = new JButton();
        week.setText("周");
        week.setBounds(440,60,60,20);
        week.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.week();
        });
        JF.getContentPane().add(week);

        JButton special = new JButton();
        special.setText("特殊");
        special.setBounds(440,5,60,20);
        special.addActionListener(e -> {
            cronCreate.childJF.setVisible(false);
            cronCreate.childJF = new JFrame();
            cronCreate.special();
        });
        JF.getContentPane().add(special);

        JLabel generate = new JLabel("生成值为：");
        generate.setBounds(300,90,100,20);
        JF.getContentPane().add(generate);

        cronCreate.createSimulation.setBounds(300,115,200,20);
        JF.getContentPane().add(cronCreate.createSimulation);

        //模拟十次运行效果
        JLabel simulationRemind = new JLabel("连续十次的运行模拟结果为：");
        simulationRemind.setForeground(Color.red);
        simulationRemind.setBounds(300,140,180,20);
        JF.getContentPane().add(simulationRemind);

        JTextArea simulationText = new JTextArea();
        simulationText.setBounds(300,160,180,165);
        simulationText.setEditable(false);
        JF.getContentPane().add(simulationText);

        JButton simulation = new JButton("模拟");
        simulation.setBounds(400,90,100,20);
        simulation.addActionListener(e -> {
            StringBuilder text = new StringBuilder();
            LocalDateTime time = LocalDateTime.now();
            for (int i = 0; i < 10; i++) {
                CronExpression cronExpression = CronExpression.parse(cronCreate.createSimulation.getText());
                LocalDateTime nextTime = cronExpression.next(time);
                if (nextTime == null) {
                    break;
                }
                String now;
                if (nextTime.getSecond() == 0) {
                    now = nextTime.toString().replace("T", "  ") + ":00";
                } else {
                    now = nextTime.toString().replace("T", "  ");
                }
                text.append(now);
                text.append("\n");
                time = nextTime;
            }
            simulationText.setText(text.toString());
        });
        JF.getContentPane().add(simulation);

        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 290, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            ymlInfo.setBiliJob(biliJobIn.getText());
            ymlInfo.setBirthdayJob(birthdayJobIn.getText());
            ymlInfo.setLookWorldJob(lookWorldJobIn.getText());
            ymlInfo.setUpdateJob(updateJobIn.getText());
            ymlInfo.setExterminateJob(exterminateJobIn.getText());
            ymlInfo.setCleanJob(cleanJobIn.getText());
            ymlInfo.setDayJob(dayJobIn.getText());
            ymlInfo.setPicJob(picJobIn.getText());
            ymlInfo.setMonthJob(monthJobIn.getText());
            update();
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 290, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void baiduConfig(){
        JFrame JF = new JFrame("百度配置填写");
        JF.setBounds(100, 100, 530, 314);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel identify = new JLabel("百度识图：");
        identify.setFont(font);
        identify.setBounds(10, 20, 300, 20);
        JF.getContentPane().add(identify);

        JLabel identifyAPP_ID = new JLabel("AppID:");
        identifyAPP_ID.setFont(font);
        identifyAPP_ID.setBounds(10, 40, 100, 20);
        JF.getContentPane().add(identifyAPP_ID);

        JTextField identifyAPP_IDIn = new JTextField();
        identifyAPP_IDIn.setColumns(50);
        identifyAPP_IDIn.setBounds(110, 40, 138, 20);
        JF.getContentPane().add(identifyAPP_IDIn);

        JLabel identifyAPI_KEY = new JLabel("API Key:");
        identifyAPI_KEY.setFont(font);
        identifyAPI_KEY.setBounds(10, 70, 100, 20);
        JF.getContentPane().add(identifyAPI_KEY);

        JTextField identifyAPI_KEYIn = new JTextField();
        identifyAPI_KEYIn.setColumns(50);
        identifyAPI_KEYIn.setBounds(110, 70, 138, 20);
        JF.getContentPane().add(identifyAPI_KEYIn);

        JLabel identifySECRET_KEY = new JLabel("Secret Key:");
        identifySECRET_KEY.setFont(font);
        identifySECRET_KEY.setBounds(10, 100, 100, 20);
        JF.getContentPane().add(identifySECRET_KEY);

        JTextField identifySECRET_KEYIn = new JTextField();
        identifySECRET_KEYIn.setColumns(50);
        identifySECRET_KEYIn.setBounds(110, 100, 138, 20);
        JF.getContentPane().add(identifySECRET_KEYIn);

        JLabel Audit = new JLabel("百度审核：");
        Audit.setFont(font);
        Audit.setBounds(10, 130, 300, 20);
        JF.getContentPane().add(Audit);

        JLabel AuditAPP_ID = new JLabel("AppID:");
        AuditAPP_ID.setFont(font);
        AuditAPP_ID.setBounds(10, 150, 100, 20);
        JF.getContentPane().add(AuditAPP_ID);

        JTextField AuditAPP_IDIn = new JTextField();
        AuditAPP_IDIn.setColumns(50);
        AuditAPP_IDIn.setBounds(110, 150, 138, 20);
        JF.getContentPane().add(AuditAPP_IDIn);

        JLabel AuditAPI_KEY = new JLabel("API Key:");
        AuditAPI_KEY.setFont(font);
        AuditAPI_KEY.setBounds(10, 180, 100, 20);
        JF.getContentPane().add(AuditAPI_KEY);

        JTextField AuditAPI_KEYIn = new JTextField();
        AuditAPI_KEYIn.setColumns(50);
        AuditAPI_KEYIn.setBounds(110, 180, 138, 20);
        JF.getContentPane().add(AuditAPI_KEYIn);

        JLabel AuditSECRET_KEY = new JLabel("Secret Key:");
        AuditSECRET_KEY.setFont(font);
        AuditSECRET_KEY.setBounds(10, 210, 100, 20);
        JF.getContentPane().add(AuditSECRET_KEY);

        JTextField AuditSECRET_KEYIn = new JTextField();
        AuditSECRET_KEYIn.setColumns(50);
        AuditSECRET_KEYIn.setBounds(110, 210, 138, 20);
        JF.getContentPane().add(AuditSECRET_KEYIn);

        JLabel remind = new JLabel();
        String text = "百度识图信息用于公招识别功能，如不使用可以不填写<br/>" +
                "<br/>" +
                "百度审核信息用于存图功能，如不使用可以不填写<br/>" +
                "<br/>" +
                "具体获取方法请参照文档说明<br/>";
        remind.setText(JLabelHead+text+JLabelTail);
        remind.setFont(font);
        remind.setForeground(Color.red);
        remind.setBounds(260, 0, 240, 200);
        JF.getContentPane().add(remind);

        JTextArea syURL = new JTextArea();
        syURL.setLineWrap(true);
        syURL.setText("百度识图：\n" +
                "https://cloud.baidu.com/product/ocr_general\n" +
                "百度审核：\n" +
                "https://cloud.baidu.com/product/imagecensoring");
        syURL.setEditable(false);
        syURL.setBounds(260, 170, 250, 80);
        JF.getContentPane().add(syURL);


        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 240, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            ymlInfo.setIdentifyAPP_ID(identifyAPP_IDIn.getText());
            ymlInfo.setIdentifyAPI_KEY(identifyAPI_KEYIn.getText());
            ymlInfo.setIdentifySECRET_KEY(identifySECRET_KEYIn.getText());
            ymlInfo.setAuditAPP_ID(AuditAPP_IDIn.getText());
            ymlInfo.setAuditAPI_KEY(AuditAPI_KEYIn.getText());
            ymlInfo.setAuditSECRET_KEY(AuditSECRET_KEYIn.getText());
            update();
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 240, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void API(){
        JFrame JF = new JFrame("API配置填写");
        JF.setBounds(100, 100, 500, 190);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JLabel SyAPI = new JLabel("API token:");
        SyAPI.setFont(font);
        SyAPI.setBounds(20, 20, 100, 20);
        JF.getContentPane().add(SyAPI);

        JTextField syToken = new JTextField();
        syToken.setColumns(50);
        syToken.setBounds(110, 20, 130, 20);
        JF.getContentPane().add(syToken);

        JLabel remind = new JLabel();
        String text = "有部分功能使用了随缘API的功能，未填写此可能造成该部分功能无法访问<br/>" +
                "如不需要使用该部分功能，可以不填写，如果需要使用，请自行申请<br/>" +
                "随缘API网址：";
        remind.setText(JLabelHead+text+JLabelTail);
        remind.setFont(font);
        remind.setForeground(Color.red);
        remind.setBounds(250, 10, 240, 115);
        JF.getContentPane().add(remind);

        JTextField syURL = new JTextField();
        syURL.setText("https://www.sybapi.cc/");
        syURL.setEditable(false);
        syURL.setBounds(250, 125, 150, 20);
        JF.getContentPane().add(syURL);

        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(40, 55, 70, 20);
        btn_Button_pass.addActionListener(e1 -> {
            ymlInfo.setAPIToken(syToken.getText());
            update();
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(130, 55, 70, 20);
        btn_Button_back.addActionListener(e1 -> {
            JF.setVisible(false);
            frame.setVisible(true);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    private void update(){
        String text = "<html>端口号："+ymlInfo.getPost()+"<br/>" +
                "bot名字："+ymlInfo.getBotName()+"<br/>" +
                "QQ号："+ymlInfo.getQqList()+"<br/>" +
                "密码："+ymlInfo.getPwList()+"<br/>" +
                "登录协议："+ymlInfo.getTypeList()+"<br/>" +
                "号主账号："+ymlInfo.getOwnerQQ()+"<br/>" +
                "管理账号："+ymlInfo.getAdminQQ()+"<br/>" +
                "测试群号："+ymlInfo.getTestGroup()+"<br/>" +
                "B站动态监听："+ymlInfo.getBiliJob()+"<br/>" +
                "干员庆生发送："+ymlInfo.getBirthdayJob()+"<br/>" +
                "60秒看世界发送："+ymlInfo.getLookWorldJob()+"<br/>" +
                "数据更新监听："+ymlInfo.getUpdateJob()+"<br/>" +
                "剿灭提醒发送："+ymlInfo.getExterminateJob()+"<br/>" +
                "抽卡次数更新："+ymlInfo.getCleanJob()+"<br/>" +
                "每日任务定时："+ymlInfo.getDayJob()+"<br/>" +
                "60秒看世界获取："+ymlInfo.getPicJob()+"<br/>" +
                "每月任务刷新："+ymlInfo.getMonthJob()+"<br/>" +
                "百度审核配置<br/>" +
                "AppID："+ymlInfo.getIdentifyAPP_ID()+"<br/>" +
                "API Key："+ymlInfo.getIdentifyAPI_KEY()+"<br/>" +
                "Secret Key"+ymlInfo.getIdentifySECRET_KEY()+"<br/>" +
                "百度识图配置<br/>" +
                "AppID："+ymlInfo.getAuditAPP_ID()+"<br/>" +
                "API Key："+ymlInfo.getAuditAPI_KEY()+"<br/>" +
                "Secret Key"+ymlInfo.getAuditSECRET_KEY()+"<br/>" +
                "API token："+ymlInfo.getAPIToken()+"<br/>" +
                "</html>";
        show.setText(text);
    }

    private void read() {
        Yaml yaml = new Yaml();
        LinkedHashMap<String, LinkedHashMap<String, Object>> linkedHashMap;
        try(FileInputStream inputStream = new FileInputStream("application.yml")) {
            linkedHashMap = yaml.load(inputStream);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"未找到文件，读取失败","读取错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        LinkedHashMap<String, Object> server = linkedHashMap.get("server");
        Object port = server.get("port");
        Integer integer = objectToInteger("port",port);
        ymlInfo.setPost(Objects.requireNonNullElse(integer, 3036));

        LinkedHashMap<String, Object> userConfig = linkedHashMap.get("userConfig");
        for (String key : userConfig.keySet()) {
            switch (key){
                case "botNames"-> ymlInfo.setBotName(objectToString(key,userConfig.get(key)));
                case "qqList"-> ymlInfo.setQqList(objectToString(key,userConfig.get(key)));
                case "pwList"-> ymlInfo.setPwList(objectToString(key,userConfig.get(key)));
                case "typeList"-> {
                    String s = objectToString(key,userConfig.get(key));
                    if (s.equals(""))
                        ymlInfo.setTypeList("IPAD");
                    else
                        ymlInfo.setTypeList(s);
                }
                case "ownerQQ"-> ymlInfo.setOwnerQQ(objectToString(key,userConfig.get(key)));
                case "adminQQ"-> ymlInfo.setAdminQQ(objectToString(key,userConfig.get(key)));
                case "testGroup"-> ymlInfo.setTestGroup(objectToString(key,userConfig.get(key)));
            }
        }

        LinkedHashMap<String, Object> scheduled = linkedHashMap.get("scheduled");
        for (String key : scheduled.keySet()) {
            switch (key) {
                case "biliJob" -> ymlInfo.setBiliJob(objectToString(key, scheduled.get(key)));
                case "birthdayJob" -> ymlInfo.setBirthdayJob(objectToString(key, scheduled.get(key)));
                case "lookWorldJob" -> ymlInfo.setLookWorldJob(objectToString(key, scheduled.get(key)));
                case "updateJob" -> ymlInfo.setUpdateJob(objectToString(key, scheduled.get(key)));
                case "exterminateJob" -> ymlInfo.setExterminateJob(objectToString(key, scheduled.get(key)));
                case "cleanJob" -> ymlInfo.setCleanJob(objectToString(key, scheduled.get(key)));
                case "dayJob" -> ymlInfo.setDayJob(objectToString(key, scheduled.get(key)));
                case "picJob" -> ymlInfo.setPicJob(objectToString(key, scheduled.get(key)));
                case "monthJob" -> ymlInfo.setMonthJob(objectToString(key, scheduled.get(key)));
            }
        }

        LinkedHashMap<String, Object> baiduIdentifyConfig = linkedHashMap.get("baiduIdentifyConfig");
        for (String key : baiduIdentifyConfig.keySet()) {
            switch (key) {
                case "APP_ID" -> ymlInfo.setIdentifyAPP_ID(objectToString(key, baiduIdentifyConfig.get(key)));
                case "API_KEY" -> ymlInfo.setIdentifyAPI_KEY(objectToString(key, baiduIdentifyConfig.get(key)));
                case "SECRET_KEY" -> ymlInfo.setIdentifySECRET_KEY(objectToString(key, baiduIdentifyConfig.get(key)));
            }
        }

        LinkedHashMap<String,Object> baiduAuditConfig = linkedHashMap.get("baiduAuditConfig");
        for (String key : baiduAuditConfig.keySet()) {
            switch (key) {
                case "APP_ID" -> ymlInfo.setAuditAPP_ID(objectToString(key, baiduAuditConfig.get(key)));
                case "API_KEY" -> ymlInfo.setAuditAPI_KEY(objectToString(key, baiduAuditConfig.get(key)));
                case "SECRET_KEY" -> ymlInfo.setAuditSECRET_KEY(objectToString(key, baiduAuditConfig.get(key)));
            }
        }

        LinkedHashMap<String,Object> APIConfig = linkedHashMap.get("APIConfig");
        ymlInfo.setAPIToken(objectToString("token",APIConfig.get("token")));

        JOptionPane.showMessageDialog(null,"读入成功");
        update();
        frame.setVisible(true);
    }

    /**
     * Integer格式获取和转换，无法转换单独给出来然后直接弹掉
     * @param key 进行转换的部分的信息
     * @param o 需要获取和转换的Object
     * @return Integer格式的内容
     */
    private Integer objectToInteger(String key,Object o){
        if (o instanceof Integer ){
            return (Integer) o;
        }else if (o instanceof String) {
            try {
                return Integer.valueOf((String) o);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, key + "部分存在字符串无法转换为数字，跳过读取", "读取错误", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        }else if (o == null){
            return null;
        }else {
            JOptionPane.showMessageDialog(null,key+"部分存在无法转换格式\n实际格式为"+ o.getClass().getTypeName() +"\n跳过读取","读取错误", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * String格式获取和转换，无法转换单独给出来然后直接弹掉
     * @param key 进行转换的部分的信息
     * @param o 需要获取和转换的Object
     * @return String格式的内容
     */
    private String objectToString(String key,Object o){
        if (o instanceof String){
            return (String) o;
        }else if (o instanceof Integer || o instanceof Long){
            return String.valueOf(o);
        }else if (o == null){
            return "";
        }else{
            JOptionPane.showMessageDialog(null,key+"部分存在无法转换格式\n实际格式为"+ o.getClass().getTypeName() +"\n跳过读取","读取错误", JOptionPane.WARNING_MESSAGE);
            return "";
        }
    }

    private void finish() throws IOException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        FileWriter fileWriter = new FileWriter("application.yml");
        Yaml yaml = new Yaml(dumperOptions);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        LinkedHashMap<String, Object> server = new LinkedHashMap<>();
        server.put("port",ymlInfo.getPost());
        map.put("server", server);

        LinkedHashMap<String, Object> main = new LinkedHashMap<>();
        main.put("allow-bean-definition-overriding",true);
        LinkedHashMap<String, Object> spring = new LinkedHashMap<>();
        spring.put("main",main);
        map.put("spring", spring);

        LinkedHashMap<String, Object> userConfig = new LinkedHashMap<>();
        userConfig.put("botNames",ymlInfo.getBotName());
        userConfig.put("qqList",ymlInfo.getQqList());
        userConfig.put("pwList",ymlInfo.getPwList());
        userConfig.put("typeList",ymlInfo.getTypeList());
        userConfig.put("ownerQQ",ymlInfo.getOwnerQQ());
        userConfig.put("adminQQ",ymlInfo.getAdminQQ());
        userConfig.put("testGroup",ymlInfo.getTestGroup());
        map.put("userConfig",userConfig);

        LinkedHashMap<String, Object> scheduled = new LinkedHashMap<>();
        scheduled.put("biliJob",ymlInfo.getBiliJob());
        scheduled.put("birthdayJob",ymlInfo.getBirthdayJob());
        scheduled.put("lookWorldJob",ymlInfo.getLookWorldJob());
        scheduled.put("updateJob",ymlInfo.getUpdateJob());
        scheduled.put("exterminateJob",ymlInfo.getExterminateJob());
        scheduled.put("cleanJob",ymlInfo.getCleanJob());
        scheduled.put("dayJob",ymlInfo.getDayJob());
        scheduled.put("picJob",ymlInfo.getPicJob());
        scheduled.put("monthJob",ymlInfo.getMonthJob());
        map.put("scheduled",scheduled);

        LinkedHashMap<String, Object> baiduIdentifyConfig = new LinkedHashMap<>();
        baiduIdentifyConfig.put("APP_ID",ymlInfo.getIdentifyAPP_ID());
        baiduIdentifyConfig.put("API_KEY",ymlInfo.getIdentifyAPI_KEY());
        baiduIdentifyConfig.put("SECRET_KEY",ymlInfo.getIdentifySECRET_KEY());
        map.put("baiduIdentifyConfig",baiduIdentifyConfig);

        LinkedHashMap<String,Object> baiduAuditConfig = new LinkedHashMap<>();
        baiduAuditConfig.put("APP_ID",ymlInfo.getAuditAPP_ID());
        baiduAuditConfig.put("API_KEY",ymlInfo.getAuditAPI_KEY());
        baiduAuditConfig.put("SECRET_KEY",ymlInfo.getAuditSECRET_KEY());
        map.put("baiduAuditConfig",baiduAuditConfig);

        LinkedHashMap<String,Object> APIConfig = new LinkedHashMap<>();
        APIConfig.put("token",ymlInfo.getAPIToken());
        map.put("APIConfig",APIConfig);

        yaml.dump(map,fileWriter);
    }
}