package net.scit.entity;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable, Comparable<Patient> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String patientId; 		// ȯ�ڹ�ȣ 
	private String name; 			// �̸�
	private int age; 				// ����
	private Department part; 		// �����ڵ�(�������)
	private int period; 			// �Կ��Ⱓ

	private int expenses; 			// ������ 
	private int hospitalBill ; 		// �Կ���

	public Patient(String patientId, String name, int age, Department part, int period) {
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.part = part;
		this.period = period;

		clacExpense();
	}

	/**
	 * 1�� �Կ� ���
	 * @return int pay
	 */
	private int dayFee() {
		int dayFee;

		if(period >= 1 && period <= 3) {
			dayFee = 30000;
		} else if(period == 0){
			dayFee = 0;
		} else {
			dayFee = 25000;
		}
		return dayFee;
	}

	/**
	 * �Կ��� ���
	 * �� �Կ���(totalFee) : �Կ��ϼ� * 1�� ���(dayFee)
	 * �Կ��� - ��û���ݾ�(hospitalBii) : ���Կ��� * ����
	 */
	private void clacExpense() {
		int totalFee = (dayFee() * period);			// �� �Կ���

		/* hospitalBill */
		if(period < 10){
			hospitalBill = (int) (totalFee * 1.0);

		} else if(period < 15) {					// 10 - 14��
			hospitalBill = (int) (totalFee * 0.85);

		} else if(period < 20) {					// 15 - 19��
			hospitalBill = (int) (totalFee * 0.80);

		}else if(period < 30) {						// 20 - 29��
			hospitalBill = (int) (totalFee * 0.77);

		}else if(period < 100) { 					// 30 - 99��
			hospitalBill = (int) (totalFee * 0.72);

		}else {										// 100�� �̻�
			hospitalBill = (int) (totalFee * 0.68);
		}

	}

	/**
	 * �� ����� ���
	 * @return int �������
	 */
	public int getTotalCost(){
		/* expenses */
		if (age < 10) {
			expenses = 7000;

		} else if (age < 20) {
 			expenses = 5000;

		} else if (age < 30) {
			expenses = 8000;

		} else if (age < 40) {
			expenses = 7000;

		} else if (age < 50) {
			expenses = 4500;

		} else {
			expenses = 2300;
		}

		return expenses + hospitalBill;			//������ + �Կ���
	}

	/* ------------------------- Getter Setter -------------------------  */

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Department getPart() {
		return part;
	}

	public void setPart(Department part) {
		this.part = part;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getExpenses() {
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	public int getHospitalBill() {
		return hospitalBill;
	}

	public void setHospitalBill(int hospitalBill) {
		this.hospitalBill = hospitalBill;
	}

	/* ------------------------- hashCode equals compareTo toString -------------------------  */

	@Override
	public int hashCode() {
		return Objects.hash(patientId, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Patient)) return false;
		Patient patient = (Patient) o;
		return getPatientId().equals(patient.getPatientId()) && getName().equals(patient.getName());
	}

	@Override
	public int compareTo(Patient o) {
		return patientId.compareTo(o.getPatientId());
	}

	@Override
	public String toString() {
		return String.format("%3s %6s %6s %3d�� %6d�� %3d�� %3d��",patientId, name, part.getType(), expenses, period, hospitalBill, getTotalCost());
	}
}
