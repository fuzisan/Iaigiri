package c0112450.sensor_acc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sensor_accActivity extends Activity {
	private TextView textView;
	private Button resetbtn;

	SampleSensorEventListener sse;
	private float[] accevalues = new float[3];
	private float[] orientvalues = new float[3];
	private Sensor accelerometer;
	private Sensor orientation;
	private SensorManager sensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// レイアウトの生成
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		setContentView(ll);
		// テキストビューの生成
		textView = new TextView(this);
		textView.setText("SensorEx");
		ll.addView(textView);
		// ボタンを作る
		resetbtn = new Button(this);
		ll.addView(resetbtn);
		sse = new SampleSensorEventListener();
		// センサー
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sensorManager.registerListener(sse, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sse, orientation,
				SensorManager.SENSOR_DELAY_NORMAL);
		// イメージ
		ImageView katana = new ImageView(this);
		katana.setImageResource(R.drawable.katana);
		ll.addView(katana);

	}

	// センサの変化があった場合に処理を行うリスナ
	class SampleSensorEventListener implements SensorEventListener {
		String text = "";

		// センサーの値が変更される毎に呼び出されるメソッド
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor == orientation) {
				orientvalues[0] = (event.values[0]);
				orientvalues[1] = (event.values[1]);
				orientvalues[2] = (event.values[2]);
			}
			if (event.sensor == accelerometer) {
				accevalues[0] = (event.values[0]);
				accevalues[1] = (event.values[1]);
				accevalues[2] = (event.values[2]);
			}
			text ="角度60で振って！！！"+ "\n角度:" + (int) orientvalues[2];

			textView.setTextSize(50.0f);
			textView.setText(text);

			if (Math.signum(orientvalues[1]) == 1
					&& Math.signum(orientvalues[2]) == 1) {

				// 加速度の総和
				float acc = Math.abs(accevalues[0]) + Math.abs(accevalues[1])
						+ Math.abs(accevalues[2]);
				String kekka = "角度" + (int) orientvalues[2] + "\n加速度"
						+ (int) acc;
				textView.setTextSize(32.0f);

				sensorManager.unregisterListener(this);
				// リセットボタンの実装
				resetbtn.setText("リセット");
				resetbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplication(),
								Sensor_accActivity.class);
						startActivity(intent);

					}
				});
				// 判定
				if ((int) orientvalues[2] == 60) {
					kekka += "\n60度ぴったり！！達人";

				} else if (orientvalues[2] >= 55 && orientvalues[2] < 65) {
					kekka += "\nもう少しで60度!おしい！！";

				} else {
					kekka += "\n凡人";

				}

				if (acc > 21) {
					kekka += "\n加速十分！切断！";
				} else if (acc > 10) {
					kekka += "\n加速不十分！切れ目が入った";
				} else {
					kekka += "\n速さが足りない！！切れてなーい";
				}
				textView.setText(kekka);
			}

		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

}
