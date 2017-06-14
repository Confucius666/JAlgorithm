package pedometer;

/**
 * �߲�����������ڼ���߲�������
 */
public class StepDetector
{
	public static int CURRENT_STEP = 0;
	public static float SENSITIVITY = 0;

	float vl = 0;// ��ͨ�˲�����

	private float lastValue = 0;
	private float lastDirection = 0;
	private float Extremes[] = new float[2];
	private float lastDiff = 0;
	private int lastMatch = -1;

	/**
	 * ���������ĵĹ��캯��
	 * 
	 * @param context
	 */
	public StepDetector(float sen)
	{
		SENSITIVITY = sen;

		vl = 0;
	}

	/**
	 * �Ų�̽�� һ���򷵻�true
	 * 
	 * @param values
	 * @return
	 */
	public boolean stepJudge(float v)
	{
		{
			// ���ٶȴ�����һ�� dir=1(����), С��ȡ-1�����壩������ȡ0
			float direction = (v > lastValue ? 1 : (v < lastValue ? -1 : 0));

			// ����߹�
			if (direction == -lastDirection)
			{
				// 0�ǲ��ȣ�1�ǲ��� 
				int extType = (direction > 0 ? 0 : 1);// minumum or maximum?

				Extremes[extType] = lastValue;//mLastExtemes[0]��ʾ����ֵ��[1]��ʾ����ֵ

				// mLastValues�ǲ����ֵ //�����ֵ��⣿
				float diff = Math.abs(Extremes[extType] - Extremes[1 - extType]);
				
				if (diff > SENSITIVITY)
				{
					boolean isAlmostAsLargeAsPrevious = diff > (lastDiff * 1 / 3);
					boolean isPreviousLargeEnough = lastDiff > (diff / 3);// ��֤��һ���������߱ȽϾ���
					boolean isNotContra = (lastMatch != 1 - extType);// ���ϴ���ͬ

					if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra)
					{
						CURRENT_STEP++;
						lastMatch = extType;
						
						lastDiff = diff;
						lastDirection = direction;
						lastValue = v;
						System.out.println(v);
						return true;
					} else
					{
						//lastMatch = -1;
					}
				}
				lastDiff = diff;
			}

			System.out.println(0);
			lastDirection = direction;
			lastValue = v;
			return false;
		}
	}

	// ��һ�׿������˲��Ƚ���
	private float lowPassFilter(float v)
	{
		if (vl == 0)
		{
			vl = v;
		} else
		{
			vl = vl * 0.8f + v * 0.2f;
		}
		return vl;
	}

	public void clearBuffer()
	{
		lastValue = 0;
		lastDirection = 0;

		lastDiff = 0;
		lastMatch = -1;
		vl = 0;
	}
}
