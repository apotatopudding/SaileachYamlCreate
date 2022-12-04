package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.valueOf;

public class CronCreate {

    //作用是锁定前态，方便在未选中时更新内存数据而不展示
    private int state;

    //对生成的数据进行存储
    CronInfo cronInfo = new CronInfo();

    //存储选择而产生的数据
    CronTemp cronTemp = new CronTemp();

    //临时产生的模拟数据
    JTextField simulationOut = new JTextField();{
        simulationOut.setEditable(false);
    }

    //最终生成的模拟数据
    JTextField createSimulation = new JTextField();{
        createSimulation.setEditable(false);
        create();
    }

    /*
    public static void main(String[] args) {
        CronCreate cronCreate = new CronCreate();
        cronCreate.cronCreate();
    }
    */

    private void cronCreate(){
        JFrame JF = new JFrame("cron生成器");
        JF.setBounds(100, 500, 240, 175);
        JF.setLocationRelativeTo(null);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        JButton second = new JButton();
        second.setText("秒");
        second.setBounds(10,10,60,20);
        second.addActionListener(e -> second());
        JF.getContentPane().add(second);

        JButton minute = new JButton();
        minute.setText("分钟");
        minute.setBounds(80,10,60,20);
        minute.addActionListener(e -> minute());
        JF.getContentPane().add(minute);

        JButton hour = new JButton();
        hour.setText("小时");
        hour.setBounds(150,10,60,20);
        hour.addActionListener(e -> hour());
        JF.getContentPane().add(hour);

        JButton day = new JButton();
        day.setText("日");
        day.setBounds(10,40,60,20);
        day.addActionListener(e -> day());
        JF.getContentPane().add(day);

        JButton mouth = new JButton();
        mouth.setText("月");
        mouth.setBounds(80,40,60,20);
        mouth.addActionListener(e -> mouth());
        JF.getContentPane().add(mouth);

        JButton week = new JButton();
        week.setText("周");
        week.setBounds(150,40,60,20);
        week.addActionListener(e -> week());
        JF.getContentPane().add(week);

        JLabel simulation = new JLabel("生成值为：");
        simulation.setBounds(10,70,70,20);
        JF.getContentPane().add(simulation);

        createSimulation.setBounds(80,70,100,20);
        JF.getContentPane().add(createSimulation);

        JButton btn_Button_back = new JButton("退出");
        btn_Button_back.setBounds(10, 100, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            System.exit(0);
        });
        JF.getContentPane().add(btn_Button_back);

        JF.setVisible(true);
    }

    public void second(){
        JFrame JF = new JFrame("秒生成");
        JF.setBounds(650, 100, 300, 280);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        //置入通配符
        JRadioButton second = new JRadioButton("每秒");
        buttonGroup.add(second);
        second.setBounds(10,10,60,20);
        second.addActionListener(e -> update(1));
        JF.getContentPane().add(second);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从                 -                 秒");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(1, 1, 59, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
        cycleMin.setBounds(87,40,40,20);
        cycleMin.addChangeListener(e -> {
            cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 59, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
        cycleMax.setBounds(144,40,40,20);
        cycleMax.addChangeListener(e -> {
            cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从               秒开始，每              秒执行一次");
        fromText.setBounds(32,70,250,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
        fromMin.setBounds(47,70,40,20);
        fromMin.addChangeListener(e -> {
            cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 59, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
        fromMax.setBounds(152,70,40,20);
        fromMaxRange.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定：");
        buttonGroup.add(appoint);
        appoint.setBounds(10,100,65,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JTextField appointIn = new JTextField();
        appointIn.setBounds(75,100,100,20);
        cronTemp.setAppoint("*");
        JF.getContentPane().add(appointIn);

        JButton sure = new JButton("√");
        sure.setBounds(200,100,45,20);
        sure.addActionListener(e -> {
            if (appointIn.getText().strip().equals("")){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                String[] a = appointIn.getText().split(",");
                StringBuilder b = new StringBuilder();
                List<String> list = new ArrayList<>();//用于收集已录入时间，如果出现重复时间直接跳过不再加入时间表
                for(String s : a){
                    Pattern pattern = Pattern.compile("^([0-5]?[0-9])$");//依次判断输入是否为0-59的整数
                    Matcher isNum = pattern.matcher(s);
                    if(!isNum.matches()) {
                        JOptionPane.showMessageDialog(null, "输入错误，请检查格式","错误",JOptionPane.ERROR_MESSAGE);
                        b = new StringBuilder();//清空掉已录入避免污染收录数据
                        break;
                    }else if (!list.contains(s)){
                        b.append(s);
                        b.append(",");
                        list.add(s);
                    }
                }
                if (b.length()>0) {
                    b.delete(b.length() - 1, b.length());
                    cronTemp.setAppoint(b.toString());
                    update(0);
                }
            }
        });
        JF.getContentPane().add(sure);

        JLabel appointRemind = new JLabel();
        String text = "<html>请在右侧输入框指定秒(0-59之间)<br/>" +
                "按√锁定，多项指定用英文逗号隔开<br/>" +
                "注意选择指定时输入框不可为空<br/></html>";
        appointRemind.setText(text);
        appointRemind.setForeground(Color.red);
        appointRemind.setBounds(30,125,220,45);
        JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 180, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                cronInfo.setSecond(simulationOut.getText());
                cronTemp = new CronTemp();
                create();
                simulationOut.setText(null);
                JF.setVisible(false);
            }
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 180, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,210,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,210,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    public void minute(){
        JFrame JF = new JFrame("分钟生成");
        JF.setBounds(650, 100, 305, 280);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        //置入通配符
        JRadioButton minute = new JRadioButton("每分钟");
        buttonGroup.add(minute);
        minute.setBounds(10,10,70,20);
        minute.addActionListener(e -> update(1));
        JF.getContentPane().add(minute);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从                 -                 分钟");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(1, 1, 59, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
        cycleMin.setBounds(87,40,40,20);
        cycleMin.addChangeListener(e -> {
            cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 59, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
        cycleMax.setBounds(142,40,40,20);
        cycleMax.addChangeListener(e -> {
            cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从               分钟开始，每               分钟执行一次");
        fromText.setBounds(32,70,265,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
        fromMin.setBounds(47,70,40,20);
        fromMin.addChangeListener(e -> {
            cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 59, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
        fromMax.setBounds(167,70,40,20);
        fromMaxRange.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定：");
        buttonGroup.add(appoint);
        appoint.setBounds(10,100,65,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JTextField appointIn = new JTextField();
        appointIn.setBounds(75,100,100,20);
        cronTemp.setAppoint("*");
        JF.getContentPane().add(appointIn);

        JButton sure = new JButton("√");
        sure.setBounds(200,100,45,20);
        sure.addActionListener(e -> {
            if (appointIn.getText().strip().equals("")){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                String[] a = appointIn.getText().split(",");
                StringBuilder b = new StringBuilder();
                List<String> list = new ArrayList<>();//用于收集已录入时间，如果出现重复时间直接跳过不再加入时间表
                for(String s : a){
                    Pattern pattern = Pattern.compile("^([0-5]?[0-9])$");//依次判断输入是否为0-59的整数
                    Matcher isNum = pattern.matcher(s);
                    if(!isNum.matches()) {
                        JOptionPane.showMessageDialog(null, "输入错误，请检查格式","错误",JOptionPane.ERROR_MESSAGE);
                        b = new StringBuilder();//清空掉已录入避免污染收录数据
                        break;
                    }else if (!list.contains(s)){
                        b.append(s);
                        b.append(",");
                        list.add(s);
                    }
                }
                if (b.length()>0) {
                    b.delete(b.length() - 1, b.length());
                    cronTemp.setAppoint(b.toString());
                    update(0);
                }
            }
        });
        JF.getContentPane().add(sure);

        JLabel appointRemind = new JLabel();
        String text = "<html>请在右侧输入框指定分钟(0-59之间)<br/>" +
                "按√锁定，多项指定用英文逗号隔开<br/>" +
                "注意选择指定时输入框不可为空<br/></html>";
        appointRemind.setText(text);
        appointRemind.setForeground(Color.red);
        appointRemind.setBounds(30,125,220,45);
        JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 180, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                cronInfo.setMinute(simulationOut.getText());
                cronTemp = new CronTemp();
                create();
                simulationOut.setText(null);
                JF.setVisible(false);
            }
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 180, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,210,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,210,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    public void hour(){
        JFrame JF = new JFrame("小时生成");
        JF.setBounds(650, 100, 305, 280);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        //置入通配符
        JRadioButton hour = new JRadioButton("每小时");
        buttonGroup.add(hour);
        hour.setBounds(10,10,70,20);
        hour.addActionListener(e -> update(1));
        JF.getContentPane().add(hour);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从                 -                 时");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(0, 0, 23, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
        cycleMin.setBounds(87,40,40,20);
        cycleMin.addChangeListener(e -> {
            cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 23, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
        cycleMax.setBounds(142,40,40,20);
        cycleMax.addChangeListener(e -> {
            cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从                时开始，每               小时执行一次");
        fromText.setBounds(32,70,265,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(0, 0, 23, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
        fromMin.setBounds(47,70,40,20);
        fromMin.addChangeListener(e -> {
            cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 23, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
        fromMax.setBounds(157,70,40,20);
        fromMaxRange.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定：");
        buttonGroup.add(appoint);
        appoint.setBounds(10,100,65,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JTextField appointIn = new JTextField();
        appointIn.setBounds(75,100,100,20);
        cronTemp.setAppoint("*");
        JF.getContentPane().add(appointIn);

        JButton sure = new JButton("√");
        sure.setBounds(200,100,45,20);
        sure.addActionListener(e -> {
            if (appointIn.getText().strip().equals("")){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                String[] a = appointIn.getText().split(",");
                StringBuilder b = new StringBuilder();
                List<String> list = new ArrayList<>();//用于收集已录入时间，如果出现重复时间直接跳过不再加入时间表
                for(String s : a){
                    Pattern pattern = Pattern.compile("^(2[0-3]|1?\\d|[0-9])$");//依次判断输入是否为0-23的整数
                    Matcher isNum = pattern.matcher(s);
                    if(!isNum.matches()) {
                        JOptionPane.showMessageDialog(null, "输入错误，请检查格式","错误",JOptionPane.ERROR_MESSAGE);
                        b = new StringBuilder();//清空掉已录入避免污染收录数据
                        break;
                    }else if (!list.contains(s)){
                        b.append(s);
                        b.append(",");
                        list.add(s);
                    }
                }
                if (b.length()>0) {
                    b.delete(b.length() - 1, b.length());
                    cronTemp.setAppoint(b.toString());
                    update(0);
                }
            }
        });
        JF.getContentPane().add(sure);

        JLabel appointRemind = new JLabel();
        String text = "<html>请在右侧输入框指定小时(0-23之间)<br/>" +
                "按√锁定，多项指定用英文逗号隔开<br/>" +
                "注意选择指定时输入框不可为空<br/></html>";
        appointRemind.setText(text);
        appointRemind.setForeground(Color.red);
        appointRemind.setBounds(30,125,220,45);
        JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 180, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                cronInfo.setHour(simulationOut.getText());
                cronTemp = new CronTemp();
                create();
                simulationOut.setText(null);
                JF.setVisible(false);
            }
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 180, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,210,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,210,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    public void day(){
        JFrame JF = new JFrame("日生成");
        JF.setBounds(650, 100, 305, 310);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        //置入通配符
        JRadioButton hour = new JRadioButton("每日");
        buttonGroup.add(hour);
        hour.setBounds(10,10,60,20);
        hour.addActionListener(e -> update(1));
        JF.getContentPane().add(hour);

        //置入不指定
        JRadioButton notAppoint = new JRadioButton("不指定");
        buttonGroup.add(notAppoint);
        notAppoint.setBounds(70,10,70,20);
        notAppoint.addActionListener(e -> update(5));
        JF.getContentPane().add(notAppoint);

        //置入最后一天
        JRadioButton lastDay = new JRadioButton("当月最后一天");
        buttonGroup.add(lastDay);
        lastDay.setBounds(140,10,100,20);
        lastDay.addActionListener(e -> {
            cronTemp.setLatestDay(0);
            update(6);
        });
        JF.getContentPane().add(lastDay);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从                 -                 日");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(1, 1, 31, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
        cycleMin.setBounds(87,40,40,20);
        cycleMin.addChangeListener(e -> {
            cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 31, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
        cycleMax.setBounds(142,40,40,20);
        cycleMax.addChangeListener(e -> {
            cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从                日开始，每               天执行一次");
        fromText.setBounds(32,70,265,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(1, 1, 31, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMin.getValue());
        fromMin.setBounds(47,70,40,20);
        fromMin.addChangeListener(e -> {
            cronTemp.setFromMinRange((Integer) fromMin.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 31, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMax.getValue());
        fromMax.setBounds(157,70,40,20);
        fromMax.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMax.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //按最近工作日填写
        JRadioButton workDay = new JRadioButton();
        buttonGroup.add(workDay);
        workDay.addActionListener(e -> update(7));
        workDay.setBounds(10,100,20,20);

        JF.getContentPane().add(workDay);

        JLabel workDayText = new JLabel("每月                号最近的那个工作日");
        workDayText.setBounds(32,100,265,20);
        JF.getContentPane().add(workDayText);

        SpinnerModel workDayInRange = new SpinnerNumberModel(1, 1, 31, 1);
        JSpinner workDayIn = new JSpinner(workDayInRange);
        cronTemp.setLatestWorkDay((Integer) workDayIn.getValue());
        workDayIn.setBounds(60,100,40,20);
        workDayIn.addChangeListener(e -> {
            cronTemp.setLatestWorkDay((Integer) workDayIn.getValue());
            update(0);
        });
        JF.getContentPane().add(workDayIn);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定：");
        buttonGroup.add(appoint);
        appoint.setBounds(10,130,65,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JTextField appointIn = new JTextField();
        appointIn.setBounds(75,130,100,20);
        cronTemp.setAppoint("?");
        JF.getContentPane().add(appointIn);

        JButton sure = new JButton("√");
        sure.setBounds(200,130,45,20);
        sure.addActionListener(e -> {
            if (appointIn.getText().strip().equals("")){
                cronTemp.setAppoint("?");
                update(0);
            }else {
                String[] a = appointIn.getText().split(",");
                StringBuilder b = new StringBuilder();
                List<String> list = new ArrayList<>();//用于收集已录入时间，如果出现重复时间直接跳过不再加入时间表
                for(String s : a){
                    Pattern pattern = Pattern.compile("^([1-9]|[1-2]\\d|3[0-1])$");//依次判断输入是否为1-31的整数
                    Matcher isNum = pattern.matcher(s);
                    if(!isNum.matches()) {
                        JOptionPane.showMessageDialog(null, "输入错误，请检查格式","错误",JOptionPane.ERROR_MESSAGE);
                        b = new StringBuilder();//清空掉已录入避免污染收录数据
                        break;
                    }else if (!list.contains(s)){
                        b.append(s);
                        b.append(",");
                        list.add(s);
                    }
                }
                if (b.length()>0) {
                    b.delete(b.length() - 1, b.length());
                    cronTemp.setAppoint(b.toString());
                    update(0);
                }
            }
        });
        JF.getContentPane().add(sure);

        JLabel appointRemind = new JLabel();
        String text = "<html>请在右侧输入框指定日期(1-31之间)<br/>" +
                "按√锁定，多项指定用英文逗号隔开<br/>" +
                "选择指定时输入框为空等同不指定<br/></html>";
        appointRemind.setText(text);
        appointRemind.setForeground(Color.red);
        appointRemind.setBounds(30,155,220,45);
        JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 210, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                cronInfo.setDay("?");
            }else {
                cronInfo.setDay(simulationOut.getText());
            }
            cronTemp = new CronTemp();
            create();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 210, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,240,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,240,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    public void mouth(){
        JFrame JF = new JFrame("月生成");
        JF.setBounds(650, 100, 305, 280);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        //置入通配符
        JRadioButton month = new JRadioButton("每月");
        buttonGroup.add(month);
        month.setBounds(10,10,70,20);
        month.addActionListener(e -> update(1));
        JF.getContentPane().add(month);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从                 -                 月");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(1, 1, 12, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
        cycleMin.setBounds(87,40,40,20);
        cycleMin.addChangeListener(e -> {
            cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 12, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
        cycleMax.setBounds(142,40,40,20);
        cycleMax.addChangeListener(e -> {
            cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从                月开始，每               个月执行一次");
        fromText.setBounds(32,70,265,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(1, 1, 12, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
        fromMin.setBounds(47,70,40,20);
        fromMin.addChangeListener(e -> {
            cronTemp.setFromMinRange((Integer) fromMinRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 12, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
        fromMax.setBounds(157,70,40,20);
        fromMaxRange.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMaxRange.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定：");
        buttonGroup.add(appoint);
        appoint.setBounds(10,100,65,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JTextField appointIn = new JTextField();
        appointIn.setBounds(75,100,100,20);
        cronTemp.setAppoint("*");
        JF.getContentPane().add(appointIn);

        JButton sure = new JButton("√");
        sure.setBounds(200,100,45,20);
        sure.addActionListener(e -> {
            if (appointIn.getText().strip().equals("")){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                String[] a = appointIn.getText().split(",");
                StringBuilder b = new StringBuilder();
                List<String> list = new ArrayList<>();//用于收集已录入时间，如果出现重复时间直接跳过不再加入时间表
                for(String s : a){
                    Pattern pattern = Pattern.compile("^(1[0-2]|[1-9])$");//依次判断输入是否为1-12的整数
                    Matcher isNum = pattern.matcher(s);
                    if(!isNum.matches()) {
                        JOptionPane.showMessageDialog(null, "输入错误，请检查格式","错误",JOptionPane.ERROR_MESSAGE);
                        b = new StringBuilder();//清空掉已录入避免污染收录数据
                        break;
                    }else if (!list.contains(s)){
                        b.append(s);
                        b.append(",");
                        list.add(s);
                    }
                }
                if (b.length()>0) {
                    b.delete(b.length() - 1, b.length());
                    cronTemp.setAppoint(b.toString());
                    update(0);
                }
            }
        });
        JF.getContentPane().add(sure);

        JLabel appointRemind = new JLabel();
        String text = "<html>请在右侧输入框指定月份(1-12之间)<br/>" +
                "按√锁定，多项指定用英文逗号隔开<br/>" +
                "注意选择指定时输入框不可为空<br/></html>";
        appointRemind.setText(text);
        appointRemind.setForeground(Color.red);
        appointRemind.setBounds(30,125,220,45);
        JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 180, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "'？'只能在日和星期（周）中指定使用，其作用为不指定","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                cronInfo.setMouth(simulationOut.getText());
                cronTemp = new CronTemp();
                create();
                simulationOut.setText(null);
                JF.setVisible(false);
            }
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 180, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,210,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,210,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    public void week(){
        JFrame JF = new JFrame("周生成");
        JF.setBounds(650, 100, 600, 280);
        JF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JF.setResizable(false);
        JF.getContentPane().setLayout(null);

        ButtonGroup buttonGroup = new ButtonGroup();

        JLabel remind = new JLabel();
        String text = "<html>提示：周设置中，建议只使用《不指定》，《每周》这两个选项<br/>" +
                "cron中的周设置较为反人类，如需单独设置，以下内容可能会给你带来一些理解上的帮助<br/>" +
                "（如果被绕迷糊了，建议只看填写不看模拟）<br/>" +
                "1.周域从周日开始计算，显示和实际有一天差距，如显示5，实际周四，显示1，实际周日<br/>" +
                "2.周域周期单位为一周域，当计数进入下周域后将刷新，故统计时只以一周域为单位来考虑<br/>" +
                "3.A#B代表的不是第A周的周B，实际代表意义是周B，第A周<br/>" +
                "4.单独的 L意义为每周的最后一天即周六，如 L前面有数字X，则意义为当月最后一个周X<br/>" +
                "5.上月未结束的周域会被本月计入第一个周域<br/><html/>";
        remind.setText(text);
        remind.setForeground(Color.red);
        remind.setFont(new Font("黑体",Font.BOLD,14));
        remind.setBounds(280,0,300,240);
        JF.getContentPane().add(remind);

        //置入通配符
        JRadioButton hour = new JRadioButton("每周");
        buttonGroup.add(hour);
        hour.setBounds(10,10,50,20);
        hour.addActionListener(e -> update(1));
        JF.getContentPane().add(hour);

        //置入不指定
        JRadioButton notAppoint = new JRadioButton("不指定");
        buttonGroup.add(notAppoint);
        notAppoint.setBounds(60,10,65,20);
        notAppoint.addActionListener(e -> update(5));
        JF.getContentPane().add(notAppoint);

        //置入最后一个周X
        JRadioButton lastDay = new JRadioButton("当月最后一个周");
        buttonGroup.add(lastDay);
        lastDay.setBounds(125,10,112,20);
        lastDay.addActionListener(e -> update(6));
        JF.getContentPane().add(lastDay);

        SpinnerModel lastDayRange = new SpinnerNumberModel(0, 0, 7, 1);
        JSpinner lastDayOfMonth = new JSpinner(lastDayRange);
        cronTemp.setLatestDay(0);
        lastDayOfMonth.setBounds(238,10,30,20);
        lastDayOfMonth.addChangeListener(e -> {
            if(lastDayOfMonth.getValue().equals(7)){
                cronTemp.setLatestDay(1);
            }else if(lastDayOfMonth.getValue().equals(0)){
                cronTemp.setLatestDay(0);
            }else{
                cronTemp.setLatestDay((Integer) lastDayOfMonth.getValue()+1);
            }
            update(0);
        });
        JF.getContentPane().add(lastDayOfMonth);

        //按周期填写
        JRadioButton cycle = new JRadioButton("周期：");
        buttonGroup.add(cycle);
        cycle.setBounds(10,40,62,20);
        cycle.addActionListener(e -> update(2));
        JF.getContentPane().add(cycle);

        JLabel cycleText = new JLabel("从周                 -周                 ");
        cycleText.setBounds(72,40,150,20);
        JF.getContentPane().add(cycleText);

        SpinnerModel cycleMinRange = new SpinnerNumberModel(1, 1, 7, 1);
        JSpinner cycleMin = new JSpinner(cycleMinRange);
        cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue()+1);
        cycleMin.setBounds(100,40,40,20);
        cycleMin.addChangeListener(e -> {
            if(cycleMin.getValue().equals(7)){
                cronTemp.setCycleMinRange(1);
            }else{
                cronTemp.setCycleMinRange((Integer) cycleMinRange.getValue()+1);
            }
            update(0);
        });
        JF.getContentPane().add(cycleMin);

        SpinnerModel cycleMaxRange = new SpinnerNumberModel(2, 2, 7, 1);
        JSpinner cycleMax = new JSpinner(cycleMaxRange);
        cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue()+1);
        cycleMax.setBounds(165,40,40,20);
        cycleMax.addChangeListener(e -> {
            if(cycleMax.getValue().equals(7)){
                cronTemp.setCycleMaxRange(1);
            }else{
                cronTemp.setCycleMaxRange((Integer) cycleMaxRange.getValue());
            }

            update(0);
        });
        JF.getContentPane().add(cycleMax);

        //按递增填写
        JRadioButton from = new JRadioButton();
        buttonGroup.add(from);
        from.addActionListener(e -> update(3));
        from.setBounds(10,70,20,20);

        JF.getContentPane().add(from);

        JLabel fromText = new JLabel("从周                开始，每               天执行一次");
        fromText.setBounds(32,70,265,20);
        JF.getContentPane().add(fromText);

        SpinnerModel fromMinRange = new SpinnerNumberModel(1, 1, 7, 1);
        JSpinner fromMin = new JSpinner(fromMinRange);
        cronTemp.setFromMinRange((Integer) fromMin.getValue()+1);
        fromMin.setBounds(60,70,40,20);
        fromMin.addChangeListener(e -> {
            if(fromMin.getValue().equals(7)){
                cronTemp.setFromMinRange(1);
            }else{
                cronTemp.setFromMinRange((Integer) fromMin.getValue()+1);
            }
            update(0);
        });
        JF.getContentPane().add(fromMin);

        SpinnerModel fromMaxRange = new SpinnerNumberModel(1, 1, 7, 1);
        JSpinner fromMax = new JSpinner(fromMaxRange);
        cronTemp.setFromMaxRange((Integer) fromMax.getValue());
        fromMax.setBounds(157,70,40,20);
        fromMax.addChangeListener(e -> {
            cronTemp.setFromMaxRange((Integer) fromMax.getValue());
            update(0);
        });
        JF.getContentPane().add(fromMax);

        //填写格式：A#B:周B，第A周
        JRadioButton special = new JRadioButton();
        buttonGroup.add(special);
        special.addActionListener(e -> update(8));
        special.setBounds(10,100,20,20);

        JF.getContentPane().add(special);

        JLabel specialText = new JLabel("每月第                周的周");
        specialText.setBounds(32,100,265,20);
        JF.getContentPane().add(specialText);

        SpinnerModel RangeOfSpecialInputWeek = new SpinnerNumberModel(1, 1, 5, 1);
        JSpinner specialInWeek = new JSpinner(RangeOfSpecialInputWeek);
        cronTemp.setSpecialInWeek((Integer) specialInWeek.getValue());
        specialInWeek.setBounds(72,100,40,20);
        specialInWeek.addChangeListener(e -> {
            cronTemp.setSpecialInWeek((Integer) specialInWeek.getValue());
            update(0);
        });
        JF.getContentPane().add(specialInWeek);

        SpinnerModel RangeOfSpecialInputDay = new SpinnerNumberModel(1, 1, 7, 1);
        JSpinner specialInDay = new JSpinner(RangeOfSpecialInputDay);
        cronTemp.setSpecialInDay((Integer) specialInDay.getValue()+1);
        specialInDay.setBounds(158,100,40,20);
        specialInDay.addChangeListener(e -> {
            if(specialInDay.getValue().equals(7)){
                cronTemp.setSpecialInDay(1);
            }else{
                cronTemp.setSpecialInDay((Integer) specialInDay.getValue()+1);
            }
            update(0);
        });
        JF.getContentPane().add(specialInDay);

        //按指定时间填写
        JRadioButton appoint = new JRadioButton("指定:");
        buttonGroup.add(appoint);
        appoint.setBounds(10,130,55,20);
        appoint.addActionListener(e -> update(4));
        JF.getContentPane().add(appoint);

        JCheckBox monday = new JCheckBox("一");
        monday.setBounds(65,130,40,20);
        monday.addActionListener(e -> {
            if (monday.isSelected()){
                cronTemp.appointList.add(2);
            }else {
                cronTemp.appointList.remove(valueOf(2));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(monday);

        JCheckBox tuesday = new JCheckBox("二");
        tuesday.setBounds(105,130,40,20);
        tuesday.addActionListener(e -> {
            if (tuesday.isSelected()){
                cronTemp.appointList.add(3);
            }else {
                cronTemp.appointList.remove(valueOf(3));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(tuesday);

        JCheckBox wednesday = new JCheckBox("三");
        wednesday.setBounds(145,130,40,20);
        wednesday.addActionListener(e -> {
            if (wednesday.isSelected()){
                cronTemp.appointList.add(4);
            }else {
                cronTemp.appointList.remove(valueOf(4));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(wednesday);

        JCheckBox thursday = new JCheckBox("四");
        thursday.setBounds(185,130,40,20);
        thursday.addActionListener(e -> {
            if (thursday.isSelected()){
                cronTemp.appointList.add(5);
            }else {
                cronTemp.appointList.remove(valueOf(5));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(thursday);

        JCheckBox friday = new JCheckBox("五");
        friday.setBounds(65,150,40,20);
        friday.addActionListener(e -> {
            if (friday.isSelected()){
                cronTemp.appointList.add(6);
            }else {
                cronTemp.appointList.remove(valueOf(6));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(friday);

        JCheckBox saturday = new JCheckBox("六");
        saturday.setBounds(105,150,40,20);
        saturday.addActionListener(e -> {
            if (saturday.isSelected()){
                cronTemp.appointList.add(7);
            }else {
                cronTemp.appointList.remove(valueOf(7));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(saturday);

        JCheckBox sunday = new JCheckBox("七");
        sunday.setBounds(145,150,40,20);
        sunday.addActionListener(e -> {
            if (sunday.isSelected()){
                cronTemp.appointList.add(1);
            }else {
                cronTemp.appointList.remove(valueOf(1));
            }
            appoint();
            update(0);
        });
        JF.getContentPane().add(sunday);

        //appointRemind.setBounds(30,155,220,45);
        //JF.getContentPane().add(appointRemind);

        //确认返回按钮
        JButton btn_Button_pass = new JButton("确认");
        btn_Button_pass.setBounds(50, 180, 70, 23);
        btn_Button_pass.addActionListener(e1 -> {
            if (simulationOut.getText().isEmpty()){
                cronInfo.setWeek("?");
            }else {
                cronInfo.setWeek(simulationOut.getText());
            }
            cronTemp = new CronTemp();
            create();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_pass);

        JButton btn_Button_back = new JButton("返回");
        btn_Button_back.setBounds(150, 180, 70, 23);
        btn_Button_back.addActionListener(e1 -> {
            cronTemp = new CronTemp();
            simulationOut.setText(null);
            JF.setVisible(false);
        });
        JF.getContentPane().add(btn_Button_back);

        JLabel simulation = new JLabel("模拟值为：");
        simulation.setBounds(10,210,70,20);
        JF.getContentPane().add(simulation);

        simulationOut.setBounds(80,210,100,20);
        JF.getContentPane().add(simulationOut);

        JF.setVisible(true);
    }

    private void appoint(){
        StringBuilder text = new StringBuilder();
        if (cronTemp.appointList.isEmpty()){
            text = new StringBuilder("?");
        }else {
            for(Integer i : cronTemp.appointList){
                text.append(i);
                text.append(",");
            }
            text.delete(text.length()-1,text.length());
        }
        cronTemp.setAppoint(text.toString());
    }

    private void update(int i){
        if(i == 0){
            i = state;
        }else {
            state = i;
        }
        switch (i){
            case 1 -> simulationOut.setText("*");
            case 2 -> simulationOut.setText(cronTemp.getCycleMinRange()+"-"+cronTemp.getCycleMaxRange());
            case 3 -> simulationOut.setText(cronTemp.getFromMinRange()+"/"+cronTemp.getFromMaxRange());
            case 4 -> simulationOut.setText(cronTemp.getAppoint());
            case 5 -> simulationOut.setText("?");
            case 6 -> {
                if (cronTemp.getLatestDay() == 0){
                    simulationOut.setText("L");
                }else {
                    simulationOut.setText(cronTemp.getLatestDay()+"L");
                }
            }
            case 7 -> simulationOut.setText(cronTemp.getLatestWorkDay()+"W");
            case 8 -> simulationOut.setText(cronTemp.getSpecialInDay()+"#"+cronTemp.getSpecialInWeek());
        }
    }

    private void create(){
        createSimulation.setText(cronInfo.getSecond() + " " +
                cronInfo.getMinute() + " " +
                cronInfo.getHour() + " " +
                cronInfo.getDay() + " " +
                cronInfo.getMouth() + " " +
                cronInfo.getWeek() + " "
        );
    }

    public boolean check(){
        return cronInfo.getDay().equals("?") ^ cronInfo.getWeek().equals("?");
    }

}
