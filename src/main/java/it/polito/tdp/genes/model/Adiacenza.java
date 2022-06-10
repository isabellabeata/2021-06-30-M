package it.polito.tdp.genes.model;

public class Adiacenza {
	
	private Integer chrom1;
	private Integer chrom2;
	private double peso;
	public Adiacenza(Integer chrom1, Integer chrom2, double peso) {
		super();
		this.chrom1 = chrom1;
		this.chrom2 = chrom2;

		this.peso = peso;
	}
	public Integer getChrom1() {
		return chrom1;
	}
	public void setChrom1(Integer chrom1) {
		this.chrom1 = chrom1;
	}
	public Integer getChrom2() {
		return chrom2;
	}
	public void setChrom2(Integer chrom2) {
		this.chrom2 = chrom2;
	}

	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
		
	

}
