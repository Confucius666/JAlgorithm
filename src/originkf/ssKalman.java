package originkf;

abstract class ssKalman
{
	// members
	int t;
	matrix Pk, Pkk, W, HtWinv, M, Sn, A;
	vector eps, x, xn, a;

	// default constructor
	ssKalman()
	{
		
	}

	// Algorithm 4.3 �������˲� ����Ĳ���ɾһ�ι��Ƶ�Ȩ��P�ͱ���x �Լ��۲�ֵ
	void forward(matrix Pkl, vector Xkl, vector Z)
	{
		Pk = F(t - 1).times(Pkl.times(F(t - 1).trans())).plus(Q(t - 1));       //ģ��Э�������
		W = H(t).times(Pk.times(H(t).trans())).plus(R(t));                     //
		HtWinv = W.chol(H(t)).trans();
		
		matrix Kk=Pk.times(HtWinv);                                            //����������
		vector Xk = F(t - 1).times(Xkl);                                       //ģ��Ԥ��ֵ
		eps = Z.minus(H(t).times(Xk));                                         //���̲��ࣨ�۲���Ԥ��֮�
		x=(Kk.times(eps)).plus(Xk);                                            //���¹�������
		Pkk = Pk.minus((Kk.times(H(t))).times(Pk));                            //����Э����
		
		
		matrix KkH = Pk.times(HtWinv.times(H(t))); // temporary storage
		M = F(t).minus(F(t).times(KkH));
		//Pkk = Pk.minus(KkH.times(Pk));           
		//vector Xk = F(t - 1).times(Xkl);           
		//eps = Z.minus(H(t).times(Xk));
		//x = Pk.times(HtWinv.times(eps)).plus(Xk);  
	}

	/**
	 * ״̬ת��Э����(Ԥ�����)
	 * @param i
	 * @return
	 */
	abstract matrix Q(int i);

	/**
	 * Ԥ�������״̬ת�ƣ�����
	 * @param i
	 * @return
	 */
	abstract matrix F(int i);

	/**
	 * �۲����
	 * @param i
	 * @return
	 */
	abstract matrix H(int i);

	/**
	 * �۲�����Э������
	 * @param i
	 * @return
	 */
	abstract matrix R(int i);

	// A variant of Algorithm 5.1 that initializes a and A as 0 with updating
	// at the beginning of each step rather than the end. Note the ``typo'' in the
	// text where M(t-1) is not defined for the last step with t=1. Since a and A
	// do not require updating in this case, the algorithm works as written provided
	// the last two lines of the for loop are implemented conditionally on t>1.
	void smooth(vector aIn, matrix AIn, matrix HTRinvIn, vector epsIn)
	{
		a = M.trans().times(HTRinvIn.times(epsIn)).plus(M.trans().times(aIn));
		A = M.trans().times(HTRinvIn.times(H(t + 1).times(M))).plus(M.trans().times(AIn.times(M)));
		xn = x.plus(Pk.times(a));
		Sn = Pkk.minus(Pk.times(A.times(Pk)));
	}

	// access to members

	int Gett()
	{
		return t;
	}

	matrix GetPk()
	{
		return Pk;
	}

	matrix GetPkk()
	{
		return Pkk;
	}

	matrix GetW()
	{
		return W;
	}

	matrix GetHtWinv()
	{
		return HtWinv;
	}

	matrix GetM()
	{
		return M;
	}

	matrix GetSn()
	{
		return Sn;
	}

	matrix GetA()
	{
		return A;
	}

	vector Getx()
	{
		return x;
	}

	vector Geteps()
	{
		return eps;
	}

	vector Getxn()
	{
		return xn;
	}

	// useful for setting xn=x for last step in forward
	// (first step in backward) recursion
	void Setxntox()
	{
		this.xn = new vector(x);
	}

	// useful for setting Sn=S for last step in forward
	// (first step in backward) recursion
	void SetSntoS()
	{
		this.Sn = new matrix(Pkk);
	}

	vector Geta()
	{
		return a;
	}

	// utilities

	void printQ(int i)
	{
		System.out.println("Q(" + i + ")");
		Q(i).printMatrix();
	}

	void printF(int i)
	{
		System.out.println("F(" + i + ")");
		F(i).printMatrix();
	}

	void printH(int i)
	{
		System.out.println("H(" + i + ")");
		H(i).printMatrix();
	}

	void printW(int i)
	{
		System.out.println("W(" + i + ")");
		R(i).printMatrix();
	}

	void forwardPrint()
	{
		System.out.println("eps(" + t + ")");
		eps.printVector();

		System.out.println("x(" + t + "|" + t + ")");
		x.printVector();

		System.out.println("S(" + t + "|" + t + ")");
		Pkk.printMatrix();
	}

	void backPrint()
	{
		System.out.println("x(" + t + "|n)");
		xn.printVector();
		System.out.println("S(" + t + "|n)");
		Sn.printMatrix();
	}

}
