package controlador;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import ontologia.Emprego;


public class ControladorAgente extends Agent {
	
	
	protected void setup() {
//	addBehaviour(new AerodromoEmpregoComportamento());
		//registrarServico("Apoio_Pouso");
		String serviceName = "Apoio_de_Pouso-";
	  	
	  	// Read the name of the service to register as an argument
	  	Object[] args = getArguments();
	  	if (args != null && args.length > 0) {
	  		serviceName = (String) args[0];
	  	}
		DFAgentDescription dfa = new DFAgentDescription();
		ServiceDescription svd = new ServiceDescription();
		try {
		dfa.setName(getAID());
		svd.setName(serviceName);
		svd.setType("Pouso_Comum");
		svd.addOntologies("InstrucoesVoo");
		dfa.addServices(svd);
		dfa.addOntologies(Emprego.REGRAS);
	
		DFService.register(this, dfa);

		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
		RecebeMensagens();
		gerirVoo();
		
	}
	
	protected void RecebeMensagens(){
			
		addBehaviour(new OneShotBehaviour() {	
		@Override
		public void action() {
		  	try {
				//Ontology o = myAgent.getContentManager().lookupOntology(Emprego.REGRAS);	  
			} catch (Exception e) {
		     	e.printStackTrace();
		    }}  } );	
		
		addBehaviour(new CyclicBehaviour(this){
			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					String content = msg.getContent();
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(msg.getSender().getLocalName()+ " desça para Uno-Quatro-Zero a 200 nós em direção a 11L." +
									"\n"+ getLocalName() +": Seja bem vindo a Brasília " + msg.getSender().getLocalName()+".");
					myAgent.send(reply);
				}else 	
					block();
			}//fim do action
			}); //fim do addBehaviours
		
		

	}
	
	protected void gerirVoo(){
	addBehaviour(new CyclicBehaviour(this){
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			if(msg != null) {
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("Realizar_Pouso");
				myAgent.send(reply);
			}else 	
				block();
		}
	});
	}
}