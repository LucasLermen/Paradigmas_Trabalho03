package aeronave;


import java.text.DecimalFormat;

import ambiente.Aerodromo;
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
		double autonomiaVoo = calculaAutonomia(autonomiaInicial, VELOCIDADE_VOO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_VOO+"km/h ] "
						   + "[ Em voo ] Autonomia: [ "+df.format(autonomiaVoo)+"l ]");
		//confirmarAproximacao(estado);

		//aeronave reduzindo e se aproximando da cabeceira da pista
		double autonomiaAproximacao = calculaAutonomia(autonomiaVoo, VELOCIDADE_APROXIMACAO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_APROXIMACAO+"km/h ] " + "[ Aproximando da Cabeceira ] "
				+ "Autonomia: [ "+df.format(autonomiaAproximacao)+"l ]");
			
		//aeronave pousando e reduzindo a velocidade
		double autonomiaPouso = calculaAutonomia(autonomiaAproximacao, VELOCIDADE_POUSO);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + "[ Velocidade "+VELOCIDADE_POUSO+"km/h ] " + "[ Tocando o solo ]"
				+ " Autonomia: [ "+df.format(autonomiaPouso)+"l ]");

		//aeronave iniciando o procedimento de taxiamento
		double autonomiaTaxi = calculaAutonomia(autonomiaPouso, VELOCIDADE_TAXI);
		System.out.println("Info " + myAgent.getLocalName()+ ": " + " [ Velocidade "+VELOCIDADE_TAXI+"km/h ] "
				+ "[ Taxiando na pista ] Autonomia: [ "+df.format(autonomiaTaxi)+"l ]");

		//aeronave iniciando o procedimento de taxiamento
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

	//realiza a confirmacao da posicao de aproximacao
	/*public void confirmarAproximacao(String str){
		Aerodromo ardm = new Aerodromo();
		AeronaveAgente agtAeronave = new AeronaveAgente();
		SequentialBehaviour sqb = new SequentialBehaviour();
		
					if((deslocamento(velocidade(potencia))) >= ardm.get_diametro_aerovia() && (deslocamento(velocidade(potencia))) < ardm.get_diametro_aerovia()+100 ) {
						System.out.println(myAgent.getLocalName()+" se aproximando do aerodromo");
						System.out.println(str);
					
						SolicitarPouso("Controlador", "Status"+ str);
												
						
						if( str.equalsIgnoreCase("Realizar_Pouso") == true){
							System.out.println(myAgent.getLocalName()+" iniciando processo de pouso");
							velocidade(-99);
						}	
						if( str.equalsIgnoreCase("Entre_na_fila") == true){
							System.out.println(myAgent.getLocalName()+" entrando na fila");
						}
						if( str.equalsIgnoreCase("Reduza_a_velocidade") == true){
							System.out.println(myAgent.getLocalName()+" entrando na fila");
							velocidade(VELOCIDADE_MEDIA-20);
						}
					}}
	*/

	public void action() {
			block(5000);
			regularVelocidade();

		}
		

	
	
}
