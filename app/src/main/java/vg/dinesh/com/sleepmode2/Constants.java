package vg.dinesh.com.sleepmode2;


public class Constants {

    public static final String RING_MODE = "oldRingMode" ;

    public interface ACTION {

        String PLAY_ACTION = "vg.dinesh.com.sleepmode.alertdialog.action.play";
        String MAIN_ACTION = "vg.dinesh.com.sleepmode.alertdialog.action.main";
        String STARTFOREGROUND_ACTION = "vg.dinesh.com.sleepmode.alertdialog.action.startforeground";
        String STOPFOREGROUND_ACTION = "vg.dinesh.com.sleepmode.alertdialog.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
       int FOREGROUND_SERVICE = 101;
    }

}
