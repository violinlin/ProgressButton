package violin.com.progressbutton;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by whl on 16/9/21.
 * 带进度条的button
 */

public class DownButton extends FrameLayout implements View.OnClickListener {
    private CardView actionView;
    private TextView actionText;
    private ProgressBar actionProgress;
    private boolean isStart;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            int p = msg.arg1;
            actionProgress.setProgress(p);
            actionText.setText("下载:"+p+"%");
            if (p >= 100) {
                isStart = false;
                timer.cancel();
            }
        }
    };

    public DownButton(Context context) {
        super(context);
    }

    public DownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.down_button_layout, this);
        initView();

    }

    private void initView() {
        actionView = (CardView) findViewById(R.id.actionView);
        actionView.setOnClickListener(this);
        actionText = (TextView) findViewById(R.id.actionText);
        actionProgress = (ProgressBar) findViewById(R.id.downProgressBar);

    }

    @Override
    public void onClick(View v) {
        if (!isStart) {
            startDown();
        }

    }

    Message message;
    int progress;

    private void startDown() {
        timer = new Timer("timer");
        isStart = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                message = handler.obtainMessage();
                progress+=5;
                message.arg1 = progress;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0,1000);


    }
}
