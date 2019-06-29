package ontologia;


import ambiente.Aerodromo;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;

public class Emprego extends Ontology {

  /**
    A symbolic constant, containing the name of this ontology.
   */
  public static final String NAME = "employment-ontology";

  // Vocabulario
  // Regras
  
  /**Este método concede acesso à instância única do  ontologia.
	       um objeto <code> Ontology contendo os conceitos da ontologia. **/
  
  
  public static final String AERODROMO = "AERODROMO";
  public static final String REGRAS = "regras";
  
  private static Ontology theInstance = new Emprego();

  public static Ontology getInstance() {
		return theInstance;
 }
  /**
   * Constructor
   */
  private Emprego() {
    //__CLDC_UNSUPPORTED__BEGIN
  	super(NAME, BasicOntology.getInstance());


    try {
		add(new ConceptSchema(AERODROMO), Aerodromo.class);
    	ConceptSchema cs = (ConceptSchema)getSchema(AERODROMO);

    } catch(OntologyException oe) {
        oe.printStackTrace();
        }
  }
  }