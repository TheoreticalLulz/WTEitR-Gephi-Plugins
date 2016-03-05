// PACKAGE DECLARATION
package org.gephi.plugins.example.APSPH;

// IMPORT STANDARD JAVA UTILITY LIBRARIES
import java.util.Arrays;

// IMPORT STANDARD GEPHI PACKAGES
import org.gephi.graph.api.GraphListener;
import org.gephi.graph.api.GraphEvent;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.Edge;

//============================================================================//
// FLOYD-WARSHALL ALGORITHM CLASS :                                           //
//============================================================================//
public class APSPH_FloydWarshall {
    
    /**
     * PLEASE NOTICE THE FOLLOWING : 
     *          + Each java application is subject to change without notice.
     *          + We do not consider edge weights within this application.
     *          + Each graph to be analyzed is assumed to be undirected.
     *          + This source code is tailored to the APSPH Histogram.
     */
    
    // PROTECTED EXTERNAL VISUALIZATION CLASS
    protected APSPH_Visualization histogram; 
    
    // PRIVATE GRAPH VARIABLES
    private final GraphModel    graph_model;
    private       Graph         graph;
    private       Node[]        nodes;
    
    // PRIVATE DISTANCE & EDGE MATRICES FOR ALGORITHM
    private double[][]  distance_matrix;
    private Edge[][]    connects_matrix;
    
    // PUBLIC CONSTRUCTOR ----------------------------------------------------//
    public APSPH_FloydWarshall(APSPH_Visualization hist, GraphModel model){
        
        // SET EXTERNAL CLASS INSTANCES
        this.histogram   = hist;
        this.graph_model = model;
        
        // LABEL THE AXES OF THE VISUALIZATION
        this.histogram.distance_name(  "DISTANCES" );
        this.histogram.instance_name(  "INSTANCES" );
        this.histogram.percentage_name( "INFINITY" );
        
        // ADD GRAPH LISTENER FOR AUTOMATIC REFRESH
        graph_model.addGraphListener(new GraphListener(){
        
            @Override
            public void graphChanged(GraphEvent e){
                
                clear_histog(); // CLEAR PREVIOUS HISTOGRAM DATA
                update_graph(); // UPDATE GRAPH INFORMATION
                execute_algs(); // RUN FLOYD-WARSHALL ALGORITHM
                populate_his(); // POPULATE HISTOGRAM DATA
                redraw_histo(); // REDRAW THE HISTOGRAM
                
            }
        
        });
        
    }
    
    // CLEAR PREVIOUS HISTOGRAM ----------------------------------------------//
    public void clear_histog(){
        
        histogram.clear_histogram();
        
    }
    
    // SET CURRENT GRAPH ATTRIBUTES ------------------------------------------//
    public void update_graph(){
        
        // GET COMPLETE VISIBLE GRAPH DATA
        graph = graph_model.getUndirectedGraph();
        
        // LOCK THE GRAPH TO READ DATA
        graph.readLock();
        
        // GET NODE & EDGE DATA AS ARRAYS
        nodes = graph.getNodes().toArray();
        
        // INITIALIZE EDGE & DISTANCE MATRICES
        distance_matrix = new double[nodes.length][nodes.length];
        connects_matrix = new Edge[  nodes.length][nodes.length];
        
        // UNLOCK THE GRAPH FOR POTENTIAL UPDATES
        graph.readUnlock();
        
    }
    
    // CALCULATE SHORTEST PATH DISTANCES -------------------------------------//
    public void execute_algs(){
        
        // INITIALIZE DISTANCES TO POSITIVE INFINITY
        for (int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes.length; j++){
                
                distance_matrix[i][j] = Float.POSITIVE_INFINITY;
                
            }
        }
        
        // INITIALIZE DIAGONAL INDICES TO ZERO
        for (int i = 0; i < nodes.length; i++){
            
            distance_matrix[i][i] = 0.0f;
            
        }
        
        // UPDATE DISTANCES & EDGES USING NEIGHBORHOOD EDGE-WEIGHTS
        for (int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes.length; j++){
                
                // SET INITIAL VALUES FOR MATRICES
                connects_matrix[i][j]     = graph.getEdge(nodes[i], nodes[j]);
                if(connects_matrix[i][j] != null){
                    
                    distance_matrix[i][j] = connects_matrix[i][j].getWeight();
                    
                }
                
            }
        }
        
        // UPDATE MATRICES THROUGH FLOYD-WARSHALL ALGORITHM
        for (int i = 0; i < nodes.length; i++){
            for (int v = 0; v < nodes.length; v++){
                for (int w = 0; w < nodes.length; w++){
                    
                    // UPDATE DISTANCE BY TRIANGLE INEQUALITY RULES
                    if( distance_matrix[v][w] > distance_matrix[v][i] + distance_matrix[i][w] ){
                        distance_matrix[v][w] = distance_matrix[v][i] + distance_matrix[i][w];
                    }
                    
                }
            }
        }
        
    }
    
    // POPULATE HISTOGRAM DATA WITH DISTANCES --------------------------------//
    public void populate_his(){
        
        // INITIALIZE ONE-DIMENSIONAL ARRAY FOR SIMPLER METHODS
        double[] distances = new double[nodes.length * nodes.length];
        
        // TRANSLATE TWO-DIMENSIONAL ARRAY TO ONE-DIMENSIONAL ARRAY
        int index = 0;
        for (int i = 0; i < nodes.length; i++){
            for (int j = 0; j < nodes.length; j++){
                
                // ADD DISTANCE TO ARRAY
                distances[index] = distance_matrix[i][j];
                
                // INCREMENT INDEX
                ++index;
                
            }
        }
        
        // SORT THE ARRAY FOR SIMPLICITY
        Arrays.sort(distances);
        
        // INITIALIZE COUNTER VARIABLES
        double finite_counter = 1;
        double infinite_count = 0;
        
        // LOOP THROUGH ARRAY COUNTING VALUES
        for (int i = 0; i < distances.length; i++){
            
            if( i == 0 ){
                
                // DO NOTHING
                
            } else if( distances[i] == Float.POSITIVE_INFINITY ){
                
                ++infinite_count;
                
            } else if( distances[i] == distances[i - 1] ){
                
                ++finite_counter;
                
            } else {
                
                // ADD DATE TO HISTOGRAM
                histogram.add_data( finite_counter );
                
                // RESET FINITE COUNTER VALUE
                finite_counter = 1;
                
            }
            
        }
        
        // ADD THE FINAL DATA VALUE TO THE HISTOGRAM
        histogram.add_data( finite_counter );
        
        // SET MAXIMUM POSSIBLE INFINITE COUNT
        histogram.max_infinite( (nodes.length * nodes.length) - nodes.length );
        
        // ADD INFINITE INSTANCES TO HISTOGRAM
        histogram.add_infinite( infinite_count );
        
    }
    
    // REDRAW THE HISTOGRAM VISUALIZATION ------------------------------------//
    public void redraw_histo(){
        
        histogram.retrieve_histogram( ).repaint();
        histogram.retrieve_percentage().repaint();
        
    }
    
}
//============================================================================//
//============================================================================//