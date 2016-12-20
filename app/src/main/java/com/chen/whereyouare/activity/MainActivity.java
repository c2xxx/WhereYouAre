package com.chen.whereyouare.activity;

import android.content.Intent;
import android.widget.TextView;

import com.chen.whereyouare.R;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.other.AnyEvent;
import com.chen.whereyouare.utils.MyUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_main_current_position)
    TextView tvMainCurrentPosition;

    @OnClick(R.id.btn_showLocalList)
    public void showPositionList() {
        startActivity(new Intent(this, ShowLocalPositionListActivity.class));
    }

    @OnClick(R.id.btn_showRemoteList)
    public void showRemoteList() {
        startActivity(new Intent(this, ShowRemotePositionListActivity.class));
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void onEventMainThread(AnyEvent event) {
        super.onEventMainThread(event);
        if (event.getEventType() == AnyEvent.TYPE.RECEIVE_POSITION) {
            if (event.getObj() instanceof Position) {
                Position position = (Position) event.getObj();
                String msg = "当前定位：" + position.getLocationdescribe()
                        + "\n定位时间：" + MyUtil.timeSpan2Str(position.getTimeSpan());
                tvMainCurrentPosition.setText(msg);
            }
        }
    }
}
