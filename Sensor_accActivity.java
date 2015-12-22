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
		// ���C�A�E�g�̐���
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		setContentView(ll);
		// �e�L�X�g�r���[�̐���
		textView = new TextView(this);
		textView.setText("SensorEx");
		ll.addView(textView);
		// �{�^�������
		resetbtn = new Button(this);
		ll.addView(resetbtn);
		sse = new SampleSensorEventListener();
		// �Z���T�[
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sensorManager.registerListener(sse, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sse, orientation,
				SensorManager.SENSOR_DELAY_NORMAL);
		// �C���[�W
		ImageView katana = new ImageView(this);
		katana.setImageResource(R.drawable.katana);
		ll.addView(katana);

	}

	// �Z���T�̕ω����������ꍇ�ɏ������s�����X�i
	class SampleSensorEventListener implements SensorEventListener {
		String text = "";

		// �Z���T�[�̒l���ύX����閈�ɌĂяo����郁�\�b�h
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
			text ="�p�x60�ŐU���āI�I�I"+ "\n�p�x:" + (int) orientvalues[2];

			textView.setTextSize(50.0f);
			textView.setText(text);

			if (Math.signum(orientvalues[1]) == 1
					&& Math.signum(orientvalues[2]) == 1) {

				// �����x�̑��a
				float acc = Math.abs(accevalues[0]) + Math.abs(accevalues[1])
						+ Math.abs(accevalues[2]);
				String kekka = "�p�x" + (int) orientvalues[2] + "\n�����x"
						+ (int) acc;
				textView.setTextSize(32.0f);

				sensorManager.unregisterListener(this);
				// ���Z�b�g�{�^���̎���
				resetbtn.setText("���Z�b�g");
				resetbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplication(),
								Sensor_accActivity.class);
						startActivity(intent);

					}
				});
				// ����
				if ((int) orientvalues[2] == 60) {
					kekka += "\n60�x�҂�����I�I�B�l";

				} else if (orientvalues[2] >= 55 && orientvalues[2] < 65) {
					kekka += "\n����������60�x!�������I�I";

				} else {
					kekka += "\n�}�l";

				}

				if (acc > 21) {
					kekka += "\n�����\���I�ؒf�I";
				} else if (acc > 10) {
					kekka += "\n�����s�\���I�؂�ڂ�������";
				} else {
					kekka += "\n����������Ȃ��I�I�؂�Ăȁ[��";
				}
				textView.setText(kekka);
			}

		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

}
