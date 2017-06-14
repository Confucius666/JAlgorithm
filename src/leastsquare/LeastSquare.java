package leastsquare;

/**
 * L=BX+d X��Э������ΪP��LΪm����XΪn�� m>=n<BR/>
 * v=Bx-l ����l=L-(BX0+d)<BR/>
 * ��С������ x=!(BtPB)*BtPl <BR/>
 * 
 * @author LeslieXong
 */
public class LeastSquare
{
	Matrix L,B, X,X0,P,d; // ����X
	Matrix Dx; 		//��������ֵ����
	int nL, nX; // mΪ�۲�ֵZ���� nΪ״ֵ̬X����
	double sigma;   //��λȨ����

	LeastSquare(Matrix B,Matrix d)
	{
		this.B=B;
		this.d=d;
		this.nL = B.GetNrow();
		this.nX = B.GetNcol();
		SetX0();
	}

	/**
	 * ��С����
	 * @param L
	 */
	void LSQ(Matrix L,Matrix P)
	{
		this.L=L;
		this.P=P;
		Matrix l = L.minus(B.times(X0).plus(d));
		l.printMatrix("l");

		Matrix T = B.trans().times(P).times(B).chol(new Matrix(nX, 1)); // ��������ֵ��Э������
		T.printMatrix("!Nbb");

		Matrix x = T.times(B.trans()).times(P).times(l);
		x.printMatrix("x");

		X = X0.plus(x); // ��ΪX0ȡ0 ������X=x
		X.printMatrix("X");

		Matrix v = B.times(x).minus(l);
		v.printMatrix("v");

		L = L.plus(v);
		L.printMatrix("L");

		int r = nL - nX; // ���ɶ�
		Matrix VtRV = v.trans().times(P).times(v);
		if (r >= 0)
		{
			sigma = VtRV.value(0, 0) / ((double) r);
		} else
		{
			sigma = 1.0;
		}

		System.out.printf("%8.5f",sigma);
		Dx = T.times(sigma);
	}

	/**
	 * m*n�۲�������Ǳ仯��������SetH(matrix H)
	 */
	void SetB(Matrix B)
	{
		this.B = B;
	}

	/**
	 * ����Ĭ��m*m�۲����Э������� �����Ǳ仯��������SetR(matrix R)
	 */
	void SetP(Matrix P)
	{
		this.P = P;
	}

	void Setd(Matrix d)
	{
		this.d=d;
	}
	
	/**
	 * ����ϵͳֵ��ʼֵX��Ĭ����Ϊ0
	 */
	void SetX0()
	{
		X0 = (new Matrix(nX,1,0));
		X0.printMatrix("X0");
	}

	/**
	 * ϵͳֵ��ʼֵX������
	 */
	void SetX0(Matrix x0)
	{
		this.X0=x0;
	}
	
	
	Matrix GetDxx()
	{
		return this.Dx;
	}
	
	double GetSigma()
	{
		return sigma;
	}
	
	
	void printB()
	{
		B.printMatrix("B");
	}

	void printP()
	{
		P.printMatrix("P");
	}

}
