package aeronave;

import ambiente.Aerodromo;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontologia.Emprego;
import java.util.Iterator;


public class AeronaveAgente extends Agent {
	 public static class AeronaveAgenteData {
		private String controlador;

	
	public String getControlador() {
		// TODO Auto-generated method stub
		return controlador;
	}

		public void setControlador(String controlador) {
			this.controlador = controlador;
		}
	}



	AeronaveAgenteData data = new AeronaveAgenteData();


	public AeronaveAgente(){
			MessageTemplate.MatchOntology(Emprego.AERODROMO);
}
	
	  protected void setup() {
		  
		  System.out.println(getLocalName()+" entrou no espaço aéreo de Brasília.");
		  this.data.setControlador(buscaServico("Pouso_Comum"));
		  SolicitarRegras(data.getControlador());
		  addBehaviour(new AeronaveComportamento(this, 3000));
	  }
	  	  
	  		
protected String buscaServico(String tipoServico) {
	try {
  		DFAgentDescription template = new DFAgentDescription();
  		ServiceDescription templateSd = new ServiceDescription();
  		templateSd.setType("Pouso_Comum");
  		template.addServices(templateSd);
  		
  		SearchConstraints sc = new SearchConstraints();
  		sc.setMaxResults(new Long(10));
  		
  		DFAgentDescription[] results = DFService.search(this, template, sc);
  		if (results.length > 0) {
  			for (int i = 0; i < results.length; ++i) {
  				DFAgentDescription dfd = results[i];
  				
  				AID provider = dfd.getName();
  				
  				Iterator it = dfd.getAllServices();
  			//	while (it.hasNext()) {
  					ServiceDescription sd = (ServiceDescription) it.next();
  					if (sd.getType().equals("Pouso_Comum")) {
  					  System.out.println(getLocalName()+": "+provider.getLocalName() +" solicito vetores de navegação "
  		  			  		+ "para aproximação e pouso");
  						return  provider.getLocalName();
  				}
  			//}
  		}}
  		else {
  			System.out.println(getLocalName()+" não encontrou nenhum controlador "
  					+ "e foi direcionado para outro aeroporto.");
  			doDelete();
  		}
  	}
  	catch (FIPAException fe) {
  		fe.printStackTrace();
  	}

	return " ";
	}
	


public void SolicitarRegras(String nomeAgente){
	addBehaviour(new OneShotBehaviour(this) {			
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		DFAgentDescription sfd = new DFAgentDescription();
		ServiceDescription svd = new ServiceDescription();
		
		@Override
		public void action() {
			msg.addReceiver(new AID(nomeAgente, AID.ISLOCALNAME));
			msg.setContent(getLocalName());
			myAgent.send(msg);
		}});
	
	addBehaviour(new CyclicBehaviour(this){
		@Override
		public void action() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ACLMessage msg = myAgent.receive();	
			if(msg != null) {
				Aerodromo ardm = new Aerodromo();			
				String content = msg.getContent();
				System.out.println(msg.getSender().getLocalName()+": "+content);
				
			}else {
				doDelete();	  
			}
		}
	});	
}

// Put agent clean-up operations here
	protected void takeDown() {
		System.out.println(getLocalName()+" encerrou suas atividades em Brasília.");
	}
}
