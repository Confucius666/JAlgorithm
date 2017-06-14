package coordinateconvert;
/*

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

*//**
 * ����ת��ͨ���� �Ĳ���ת�� �߲���ת�� Author: yingxiufeng Project: onemap-parent Date:
 * 2015/1/21 19:30 File: CoordinateConversion (c) Copyright gtmap Corp.2015
 *//*
public final class CoordinateConversion
{

	public static final int bj54ToXian80 = 0;

	public static final int nt94ToXian80 = 1;

	public static final int xian80ToNt94 = 2;

	private static List<Point> ptsNeedConvert = new ArrayList<Point>();

	*//***
	 * convert x y
	 * 
	 * @param type
	 * @return
	 *//*
	public double[] convert(int type)
	{
		List<Double> coords = new ArrayList<Double>();
		Bj54ToXian80 bj54ToXian = new Bj54ToXian80();
		Nt94ToXian80 nt94ToXian = new Nt94ToXian80();
		Xian80ToNt94 xian80ToNt = new Xian80ToNt94();
		Iterator<Point> iterator = ptsNeedConvert.iterator();
		while (iterator.hasNext())
		{
			Point point = iterator.next();
			double[] coord;
			switch (type)
			{
				case bj54ToXian80:
					coord = bj54ToXian.convert(point.getX(), point.getY());
					break;
				case nt94ToXian80:
					coord = nt94ToXian.convert(point.getX(), point.getY());
					break;
				case xian80ToNt94:
					coord = xian80ToNt.convert(point.getX(), point.getY());
					break;
				default:
					throw new IllegalArgumentException(String.valueOf(type) + "is not supported!");
			}
			coords.add(coord[0]);
			coords.add(coord[1]);
		}
		return org.apache.commons.lang.ArrayUtils.toPrimitive(coords.toArray(new Double[0]));
	}

	*//***
	 * �Զ����Ĳ��� ����ת��
	 * @param A
	 * @param B
	 * @param T
	 * @param K
	 * @return
	 *//*
	public double[] convert(double A, double B, double T, double K)
	{
		List<Double> coords = new ArrayList<Double>();
		CustomConversion customConversion = new CustomConversion();
		customConversion.setParams(A, B, T, K);
		Iterator<Point> iterator = ptsNeedConvert.iterator();
		while (iterator.hasNext())
		{
			Point point = iterator.next();
			double[] coord = customConversion.convert(point.getX(), point.getY());
			coords.add(coord[0]);
			coords.add(coord[1]);
		}
		return org.apache.commons.lang.ArrayUtils.toPrimitive(coords.toArray(new Double[0]));
	}

	*//***
	 * add point for conversion
	 * 
	 * @param x
	 * @param y
	 *//*
	public void addPoint(double x, double y)
	{
		ptsNeedConvert.add(new Point(x, y));
	}

	*//**
	 * clear pnts
	 *//*
	public void clear()
	{
		ptsNeedConvert = new ArrayList<Point>();
	}

	*//**
	 * validate lat long
	 *
	 * @param latitude
	 *            γ��
	 * @param longitude
	 *            ����
	 *//*
	private void validateLatLon(double latitude, double longitude)
	{
		if (latitude < -90.0 || latitude > 90.0 || longitude < -180.0
				|| longitude >= 180.0)
		{
			throw new IllegalArgumentException(
					"Legal ranges: latitude [-90,90], longitude [-180,180).");
		}
	}

	*//**
	 * ��תΪ����
	 *
	 * @param degree
	 * @return
	 *//*
	private double degreeToRadian(double degree)
	{
		return degree * Math.PI / 180;
	}

	*//**
	 * ����ת��
	 *
	 * @param radian
	 * @return
	 *//*
	private double radianToDegree(double radian)
	{
		return radian * 180 / Math.PI;
	}

	private double SIN(double value)
	{
		return Math.sin(value);
	}

	private double COS(double value)
	{
		return Math.cos(value);
	}

	*//***
	 * point
	 *//*
	private class Point
	{
		double x;
		double y;

		public Point(double X, double Y)
		{
			this.x = X;
			this.y = Y;
		}

		public double getX()
		{
			return x;
		}

		public void setX(double x)
		{
			this.x = x;
		}

		public double getY()
		{
			return y;
		}

		public void setY(double y)
		{
			this.y = y;
		}
	}

	*//***
	 * point 3d
	 *//*
	private class Point3D
	{

		double x;
		double y;
		double z;

		public Point3D(double X, double Y, double Z)
		{

			this.x = x;
			this.y = Y;
			this.z = Z;
		}

		public double getX()
		{
			return x;
		}

		public void setX(double x)
		{
			this.x = x;
		}

		public double getY()
		{
			return y;
		}

		public void setY(double y)
		{
			this.y = y;
		}

		public double getZ()
		{
			return z;
		}

		public void setZ(double z)
		{
			this.z = z;
		}
	}

	*//**
	 * ����54ת����80
	 *//*
	private class Bj54ToXian80
	{

		double a = -14.959196;

		double b = 39999982.973589;

		double t = -0.0003453875; // ����

		double k = 0.999990898222958;

		*//**
		 * convert
		 *
		 * @param x
		 * @param y
		 * @return
		 *//*
		public double[] convert(double x, double y)
		{
			double[] coord = { 0.0, 0.0 };
			double X;
			double Y;
			X = a + x * k * COS(t) - y * k * SIN(t);
			Y = b + y * k * COS(t) + x * k * SIN(t);
			coord[0] = X;
			coord[1] = Y;
			return coord;
		}
	}

	*//**
	 * ��ͨ94����תΪ����80����ϵ
	 *//*
	private class Nt94ToXian80
	{

		double a = -4373.284079;

		double b = 40127241.54955;

		double t = -0.53067909055555555555555555555556; // ��

		double k = 1.0000810804422;

		double xOffset = 3500000;

		double yOffset = 400000;

		*//**
		 * convert
		 *
		 * @param x
		 * @param y
		 * @return
		 *//*
		public double[] convert(double x, double y)
		{
			double[] coord = { 0.0, 0.0 };
			double X;
			double Y;
			if (x < xOffset)
				x = x + xOffset;
			if (y < yOffset)
				y = y + yOffset;
			X = a + x * k * COS(degreeToRadian(t)) - y * k * SIN(degreeToRadian(t));
			Y = b + y * k * COS(degreeToRadian(t)) + x * k * SIN(degreeToRadian(t));
			coord[0] = Y; // ע��x y��˳��
			coord[1] = X;
			return coord;
		}
	}

	*//**
	 * ����80תΪ��ͨ94����ϵ
	 *//*
	private class Xian80ToNt94
	{

		double a = -40522226.3359641;

		double b = -3124000.34095673;

		double t = -0.00926121180978233;

		double k = 0.999876026884487;

		*//**
		 * convert
		 *
		 * @return
		 *//*
		public double[] convert(double x, double y)
		{
			double[] coord = { 0.0, 0.0 };
			double X;
			double Y;
			X = a + x * k - y * t;
			Y = b + y * k + x * t;
			coord[0] = X;
			coord[1] = Y;
			return coord;
		}
	}

	*//**
	 * �Զ������ֵ �Խ���ת��
	 *//*
	private class CustomConversion
	{

		double a = 0;

		double b = 0;

		double t = 0;

		double k = 1;

		*//**
		 * ����ͶӰת���Ĳ��� ע�⣺�Ĳ���ֻ�����ڶ�ά���ͶӰ�任
		 *
		 * @param x0
		 *            x����ƽ��������λ��
		 * @param y0
		 *            y����ƽ��������λ��
		 * @param T
		 *            ��ת�Ƕȣ���λ��
		 * @param K
		 *            �߶�����
		 *//*
		public void setParams(double x0, double y0, double T, double K)
		{

			a = x0;
			b = y0;
			t = T;
			k = K;

		}

		*//**
		 * ����ͶӰת���߲�����ע�⣺�߲���ֻ��������ά���ͶӰ�任
		 *
		 * @param x0
		 *            x����ƽ��������λ��
		 * @param y0
		 *            y����ƽ��������λ��
		 * @param z0
		 *            z����ƽ��������λ��
		 * @param wx
		 *            x����ת�Ƕȣ���λ����
		 * @param wy
		 *            y����ת�Ƕȣ���λ����
		 * @param wz
		 *            z����ת�Ƕȣ���λ����
		 * @param K
		 *            �߶�����
		 *//*
		public void setParams(double x0, double y0, double z0, double wx, double wy, double wz,
				double K)
		{

			// TODO

		}

		*//**
		 *
		 * @param x
		 * @param y
		 * @return
		 *//*
		public double[] convert(double x, double y)
		{
			double[] coord = { 0.0, 0.0 };
			double X;
			double Y;
			X = a + x * k * COS(degreeToRadian(t)) - y * k * SIN(degreeToRadian(t));
			Y = b + y * k * COS(degreeToRadian(t)) + x * k * SIN(degreeToRadian(t));
			coord[0] = X;
			coord[1] = Y;
			return coord;
		}
	}

}
*/