// PACKAGE DECLARATION
package org.gephi.plugins.example.APSPH;

// IMPORT STANDARD OPENIDE LIBRARIES
import org.openide.windows.TopComponent;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;

// IMPORT STANDARD GEPHI PACKAGES
import org.gephi.project.api.WorkspaceListener;
import org.gephi.project.api.ProjectController;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.openide.util.Exceptions;

// TOPCOMPONENT PROPERTIES
@TopComponent.Description(
        preferredID     = "APSP Histogram", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode            = "rankingmode",
        openAtStartup   = true
)
@ActionID(
        category        = "Window",
        id              = "org.gephi.plugins.example.APSPH.APSPH"
)
@ActionReference(
        path            = "Menu/Window"
)
@TopComponent.OpenActionRegistration(
        displayName     = "#CTL_APSPHAction",
        preferredID     = "APSP Histogram"
)


//============================================================================//
// HISTOGRAM MAIN CLASS :                                                     //
//============================================================================//
public class APSPH extends TopComponent {
    
    /**
     * PLEASE NOTICE THE FOLLOWING : 
     *          + Each java application is subject to change without notice.
     *          + APSPH redirects fundamental processing to external classes.
     *          + APSPH_FloydWarshall : All-Pairs Shortest Path Processing.
     *          + APSPH_Visualization : Graphics Processing.
     */
    
    // PRIVATE & PROTECTED VARIABLE DECLARATIONS
    protected APSPH_Visualization     histogram;
    protected APSPH_FloydWarshall     algorithm;
    
    // PRIVATE GRAPH & PROJECT CONTROLLER
    private final ProjectController project_controller;
    private final GraphController   graph___controller;
    
    // PRIVATE GRAPH MODEL
    private GraphModel graph_model;
    
    // PRIVATE PANEL & LAYOUT VARIABLES
    private javax.swing.JSplitPane complete_pane;
    private java.awt.BorderLayout  completed_layout;
    
    // PUBLIC CONSTRUCTOR
    public APSPH() {
        
        // DEFINE WINDOW PARAMETERS
        setName(       NbBundle.getMessage(APSPH.class, "CTL_APSPH" ));
        setToolTipText(NbBundle.getMessage(APSPH.class, "HINT_APSPH"));
        
        // INITIALIZE CONTROLLERS
        project_controller = Lookup.getDefault().lookup(ProjectController.class);
        graph___controller = Lookup.getDefault().lookup(GraphController.class  );
        
        // INITIALIZE LAYOUT OPTIONS
        initialize_layout();
        
        // ADD WORKSPACE LISTENER
        project_controller.addWorkspaceListener(new WorkspaceListener(){

            @Override
            public void initialize(org.gephi.project.api.Workspace wrkspc) {
                  
                // NO ACTION IS CURRENTLY TAKEN
                
            }

            @Override
            public void select(org.gephi.project.api.Workspace wrkspc) {

                // INITIALIZE RELEVANT CLASSES AND SET LAYOUT
                initialize_components();
                
            }

            @Override
            public void unselect(org.gephi.project.api.Workspace wrkspc) {

                // NO ACTION IS CURRENTLY TAKEN
                
            }

            @Override
            public void close(org.gephi.project.api.Workspace wrkspc) {
                
                // CLEAR CURRENT WORKSPACE
                histogram.clear_histogram( );
                histogram.clear_percentage();
                
                // REDRAW VISUALIZATIONS
                histogram.retrieve_histogram( ).repaint();
                histogram.retrieve_percentage().repaint();
                
                // REVERT CLASSES TO THEIR NULL STATE
                histogram   = null;
                algorithm   = null;
                graph_model = null;
                
                // CLEAR LAYOUT COMPONENTS
                complete_pane.removeAll();
                
            }

            @Override
            public void disable() {

                // NO ACTION IS CURRENTLY TAKEN
                
            }
        
        });
        
    }
    
    // INITIALIZE SPLIT PANE LAYOUT
    private void initialize_layout() {
        
        // INITIALIZE SPLIT PANE TO SHOW HISTOGRAM AND PERCENTAGE GRAPHICS
        complete_pane    = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
        
        // INITIALIZE GROUP LAYOUT VARIABLES
        completed_layout = new java.awt.BorderLayout(); 
        
    }
    
    // INITIALIZE AND POPULATE THE WINDOW
    private void initialize_components() {

        // SET LAYOUT FOR FULL WINDOW
        this.setLayout( completed_layout );
        
        // SET THE SIZE OF THE DIVIDER BETWEEN PANES
        complete_pane.setDividerSize(   10 );
        
        // SET THE SPACE DISTRIBUTION OF SUBPANELS
        complete_pane.setResizeWeight( 0.9 );
        
        // LET COMPONENTS BE CONTINUOUSLY REDRAWN AS LAYOUT CHANGES
        complete_pane.setContinuousLayout(true);
        
        // INITIALIZE GRAPH MODEL
        graph_model      = graph___controller.getModel();
        
        // INITIALIZE EXTERNAL CLASS INSTANCES
        histogram        = new APSPH_Visualization(                      );
        algorithm        = new APSPH_FloydWarshall(histogram, graph_model);
        
        // ADD THE GRAPHICS PANELS
        complete_pane.setLeftComponent(  histogram.retrieve_histogram()  );
        complete_pane.setRightComponent( histogram.retrieve_percentage() );
        
        // UPDATE THE USER INTERFACE
        complete_pane.updateUI();
        
        // ADD SPLIT PANE TO WINDOW
        add( complete_pane, java.awt.BorderLayout.CENTER );
        
    }
    
}
//============================================================================//
//============================================================================//
