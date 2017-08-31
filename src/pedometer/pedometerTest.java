package pedometer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class pedometerTest
{
	public static void main(String[] args)
	{
		List<float[]> accData = readAccData("src//pedometer//acc.txt");
		List<Float> data = myDetector(accData);
		for (int i = 0; i < data.size(); i++)
		{
			//System.out.println(data.get(i));
		}
	}

	//���ԼƲ���Ч�� ������������ʱ�䴰��
	//�ӳٰ벽
	public static List<Float> myDetector(List<float[]> accData)
	{
		float vl = -10;
		List<Float> lowPassData = new LinkedList<Float>();
		for (int i = 0; i < accData.size(); i++)
		{
			float vSum = 0;
			for (int j = 0; j < 3; j++)
			{
				final float v = accData.get(i)[j];
				vSum += v;
			}

			if (vl == -10)
			{
				vl = vSum / 3f;
			}

			float v = vl * 0.8f + (vSum / 3f) * 0.2f;
			vl = v;
			lowPassData.add(v);
		}

		float peakBetweenValley = 0;
		float tempPeak = 0;
		List<Float> filterData = new LinkedList<Float>();

		float valleyLeft = 0;
		float valleyRight = 0;
		float MIN_FUSION = 0.6f;
		float MIN_STEP = 0.7f;

		for (int i = 1; i < (lowPassData.size() - 1); i++)
		{
			if (lowPassData.get(i) >= lowPassData.get(i - 1) && lowPassData.get(i) >= lowPassData.get(i + 1)) //this is a peak
			{
				float currentPeak = lowPassData.get(i);
				float diff1 = tempPeak - valleyRight;
				float diff2 = currentPeak - valleyRight;

				if (diff2 <= MIN_FUSION || diff1 <= MIN_FUSION) //�úϲ�����ǰ���ϲ��Ĳ��嵽tempPeak 
				{
					if (diff1 > diff2) //���в��壬���ܲ��ȿ��ܼ������У����Բ���tempPeak
					{

					} else//���в��壬���ܲ�����ܼ�������
					{
						tempPeak = currentPeak;
						peakBetweenValley = currentPeak;

						System.out.println(0 + " " + currentPeak);
						filterData.add(currentPeak);
					}

				} else//һ�����Ҿ���Ĳ��ȣ���Ϊ��ʼ�µ�һ���ˣ����Գ��ԼƲ� 
				{
					if (peakBetweenValley != 0)
					{
						diff1 = peakBetweenValley - valleyLeft;
						diff2 = peakBetweenValley - valleyRight;

						if (diff1 >= MIN_STEP && diff2 >= MIN_STEP) //��һ��
						{
							System.out.println(peakBetweenValley + " " + valleyRight);//���������һ��
							System.out.println(0 + " " + currentPeak);//����������Ĳ���
						} else
						{
							System.out.println(0 + " " + valleyRight);//û�Ʋ��Ĳ��岨��
							System.out.println(0 + "" + currentPeak);
						}
					}

					System.out.println(0+" "+currentPeak);
					filterData.add(valleyRight);
					filterData.add(currentPeak);

					valleyLeft = valleyRight;
					tempPeak = currentPeak;
					peakBetweenValley = currentPeak;
				}
			} else if (lowPassData.get(i) < lowPassData.get(i - 1) && lowPassData.get(i) < lowPassData.get(i + 1)) //this is a Valley
			{
				valleyRight = lowPassData.get(i);
				if (peakBetweenValley != 0)
				{
					float diff1 = peakBetweenValley - valleyLeft;
					float diff2 = peakBetweenValley - valleyRight;

					if (diff1 >= MIN_STEP && diff2 >= MIN_STEP) //��һ��
					{
						System.out.println(peakBetweenValley + " " + valleyRight);
						//System.out.println(0 + " " + lowPassData.get(i));
						peakBetweenValley = 0;
					}
				}

			} else
			{
				continue;
			}
		}

		return filterData;
	}

	private static List<float[]> readAccData(String path)
	{
		List<float[]> data = new LinkedList<float[]>();

		try
		{
			File file = new File(path);

			if (!file.exists()) // 
			{
				System.out.println("wrong file path! ");
				return data;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String temp = reader.readLine();
			while (true)
			{
				if (temp != null)
				{
					String[] a = temp.split(" ");
					float[] ac = { Float.parseFloat(a[0]), Float.parseFloat(a[1]), Float.parseFloat(a[2]) };
					data.add(ac);

					temp = reader.readLine();
				} else
				{
					reader.close();
					break;
				}
			}

			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return data;
	}

	}
