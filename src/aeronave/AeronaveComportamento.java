package aeronave;


import java.text.DecimalFormat;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AeronaveComportamento extends CyclicBehaviour{
	long delay;
	final int VELOCIDADE_VOO = 840;//km/h
	final int VELOCIDADE_APROXIMACAO = 460;//km/h
	final int VELOCIDADE_POUSO = 260;//km/h
	final int VELOCIDADE_TAXI = 20;//km/h
	final int VELOCIDADE_ESTACIONAMENTO = 0;//km/h
	double autonomiaInicial = 50;//l
	Agent agt;
	DecimalFormat df = new DecimalFormat("###,##0.00");

	public AeronaveComportamento(Agent a, long delay){
	super(a); 
	this.agt = a;
	this.delay = delay;
	}
		
		
	// calculo da autonomia
	public double calculaAutonomia(double autonomia, int velocidade) {
		return	autonomia = autonomia - ((0.08333*velocidade)*0.1);
		// 0,08333 = Valor para calcular quantos Km o avião andará em 5 min,
		//			 tempo de duração aproximado de cada procedimento.
		// 0,1 = Valor aproximado de km/l de um boeing 737-300
	}	
	
	//regula a velocidade para pouso
	public void regularVelocidade(){
		//aeronave em voo
		tempoResposta(5000);
		double autonomiaVoo = calculaAutonomia(autonomiaInicial, VELOCIDADE_VOO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_VOO+"km/h ] "
						   + "[ Em voo ] Autonomia: [ "+df.format(autonomiaVoo)+"l ]");
		//confirmarAproximacao(estado);

		//aeronave reduzindo e se aproximando da cabeceira da pista
		tempoResposta(5000);
		double autonomiaAproximacao = calculaAutonomia(autonomiaVoo, VELOCIDADE_APROXIMACAO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_APROXIMACAO+"km/h ] " + "[ Aproximando da Cabeceira ] "
				+ "Autonomia: [ "+df.format(autonomiaAproximacao)+"l ]");
			
		//aeronave pousando e reduzindo a velocidade
		tempoResposta(5000);
		double autonomiaPouso = calculaAutonomia(autonomiaAproximacao, VELOCIDADE_POUSO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_POUSO+"km/h ] " + "[ Tocando o solo ]"
				+ " Autonomia: [ "+df.format(autonomiaPouso)+"l ]");

		//aeronave iniciando o procedimento de taxiamento
		tempoResposta(5000);

		double autonomiaTaxi = calculaAutonomia(autonomiaPouso, VELOCIDADE_TAXI);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + " [ Velocidade "+VELOCIDADE_TAXI+"km/h ] "
				+ "[ Taxiando na pista ] Autonomia: [ "+df.format(autonomiaTaxi)+"l ]");

		//aeronave iniciando o procedimento de taxiamento
		tempoResposta(5000);
		System.out.println("Info " +myAgent.getLocalName()+ ": " + " [ Velocidade "+VELOCIDADE_ESTACIONAMENTO+"km/h ] " + " [ Estacionada ]");
		
	}

	//solicita ao controlador pouso da aeronave
	public String SolicitarPouso(String nomeAgente, String resp){
		SequentialBehaviour sqb = new SequentialBehaviour();
		
		System.out.println("enviar"+resp+"cotr"+nomeAgente);
		sqb.addSubBehaviour(new OneShotBehaviour(agt) {			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			DFAgentDescription sfd = new DFAgentDescription();
			ServiceDescription svd = new ServiceDescription();
			
			
			@Override
			public void action() {
				System.out.println(myAgent.getLocalName()+ ": " + "enviando situação de voo a "+ nomeAgente);
				msg.addReceiver(new AID(nomeAgente, AID.ISLOCALNAME));
				msg.setContent(resp);
				
				myAgent.send(msg);
				
				}});
		sqb.addSubBehaviour(new CyclicBehaviour(agt){
			@Override
			public void action() {
			ACLMessage msg = myAgent.receive();	
				if(msg != null) {
					System.out.println(msg.getContent());
				}}});
			return resp;
	}

	public void action() {
			block(5000);
			regularVelocidade();
	}
	
	public void tempoResposta(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
