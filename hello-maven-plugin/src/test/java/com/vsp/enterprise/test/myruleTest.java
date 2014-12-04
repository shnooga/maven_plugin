public class myruleTest extends RuleHarness {

	public String getRuleFileName() { return "myruleFaux.drl"; }

	/**
	 * JUnit will call this before every test.
	 */
	@Before
	public void setUp() throws Exception {
		createKnowledgeSession();
//		Uncomment the below to see rules being invoked
//      getKnowledgeSession().addEventListener( new DebugAgendaEventListener() );
//     	getKnowledgeSession().addEventListener( new DebugWorkingMemoryEventListener() );
		
//     	getKnowledgeSession().insert(yourFact);
	}

	/**
	 * JUnit will call this after every test
	 */
	@After
	public void tearDown() throws Exception {
		disposeKnowledgeSession();
	}

	@Test
	public final void scenario1() {
		getKnowledgeSession().insert(null);
		getKnowledgeSession().fireAllRules();
		assertThat("Goodbye", is("Hello"));
	}
}
