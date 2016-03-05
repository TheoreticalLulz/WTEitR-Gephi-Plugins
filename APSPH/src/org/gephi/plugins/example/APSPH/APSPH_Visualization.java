// PACKAGE DECLARATION
package org.gephi.plugins.example.APSPH;

// IMPORT STANDARD JAVA AWT LIBRARIES
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

// IMPORT STANDARD JAVA UTILITIES
import java.util.ArrayList;
import java.util.List;

// IMPORT STANDARD JAVA SWING LIBRARIES
import javax.swing.JPanel;

// IMPORT STANDARD JAVA COLLECTIONS LIBRARIES
import static java.util.Collections.max;
import static java.util.Collections.min;




//============================================================================//
// HISTOGRAM VISUALIZATION CLASS :                                            //
//============================================================================//
public class APSPH_Visualization {
    
    /**
     * PLEASE NOTICE THE FOLLOWING : 
     *          + Each java application is subject to change without notice.
     *          + We assume that no scale is necessary on the vertical axis.
     *          + Float, double, integer and long are permissable data types.
     *          + Functionality for layered histograms is not supported.
     *          + Colors cannot currently be set in the histogram.
     *          + A small visualization for infinite values is implemented.
     */
    
    // PRIVATE JPANEL INSTANCE FOR JAVA WINDOW
    private JPanel histogram_panel;
    private JPanel percentage_panel;
    
    // PRIVATE ARRAY FOR HISTOGRAM DATA
    private List<Double> histogram_data;
    
    // PRIVATE VARIABLE FOR PERCENTAGE INSTANCES
    private double percentage_maximum;
    private double percentage_amounts;
    
    // PRIVATE VARIABLES FOR DIMENSION CONSTRAINT
    private int histogram_height;
    private int histogram__width;
    private int percentage_height;
    private int percentage_width;
    
    // PRIVATE VARIABLES FOR HISTOGRAM RANGE
    private double minimum_value;
    private double maximum_value;
    
    // PRIVATE VARIABLES FOR LABELLING AXES
    private String name_percentage;
    private String name_instances;
    private String name_distances;
    
    
    // PUBLIC CONSTRUCTOR ----------------------------------------------------//
    public APSPH_Visualization(){
        
        // CLEAR HISTOGRAM ATTRIBUTES
        clear_histogram();
        
        // CLEAR PERCENTAGE ATTRIBUTES
        clear_percentage();
        
    }
    
    // CLEAR THE HISTOGRAM AND RE-INITIALIZE VALUES --------------------------//
    public void clear_histogram(){
        
        // INITIALIZE HISTOGRAM DATA
        histogram_data = new ArrayList<Double>();
        
        // INITIALIZE HISTOGRAM RANGE
        minimum_value  = Double.POSITIVE_INFINITY;
        maximum_value  = Double.NEGATIVE_INFINITY;
        
    }
    
    // CLEAR THE HISTOGRAM AND RE-INITIALIZE VALUES --------------------------//
    public void clear_percentage(){
        
        // INITIALIZE INFINITE SETTINGS
        percentage_amounts  = Double.NaN;
        percentage_maximum  = Double.NaN;
        
    }
    
    // CONVERT OBJECT DATA VALUE TO DOUBLES ----------------------------------//
    public void add_data(Object value){
        
        // CONVERT SEVERAL DATA TYPES TO DOUBLES
        if(        value instanceof Double  ){
            
            add_data(   (Double)  value );
            
        } else if( value instanceof Float   ){
            
            add_data( ( (Float)   value ).doubleValue() );
                    
        } else if( value instanceof Integer ){
            
            add_data( ( (Integer) value ).doubleValue() );
            
        } else if( value instanceof Long    ){
            
            add_data( ( (Long)    value ).doubleValue() );
            
        } else {
            
            throw new UnsupportedOperationException("[ERROR] : HISTOGRAM VISUALIZATION DOES NOT RECOGNIZE DATA TYPE!");
            
        }
        
    }
    
    // ADD OBJECT VALUE TO HISTOGRAM DATA ------------------------------------//
    private void add_data( Double value ){
        
        // ADD DATA TO HISTOGRAM LIST
        this.histogram_data.add(value);
        
        // UPDATE MINIMUM & MAXIMUM DATA VALUES
        minimum_value = Math.min(minimum_value, value);
        maximum_value = Math.max(maximum_value, value);
        
    }

    // DELETE OBJECT VALUE FROM HISTOGRAM DATA ------------------------------------//
    public void delete_data( int value ){
        
        // ADD DATA TO HISTOGRAM LIST
        this.histogram_data.remove(value);
        
        // UPDATE MINIMUM & MAXIMUM DATA VALUES
        minimum_value = min( this.histogram_data );
        maximum_value = max( this.histogram_data );
        
    }

    // ADD OBJECT VALUE TO HISTOGRAM DATA ------------------------------------//
    public int data_size(){
        
        return this.histogram_data.size();
        
    }
    
    // ADD INFINITE INSTANCES TO HISTOGRAM DATA ------------------------------//
    public void add_infinite( double value ){
        
        percentage_amounts = value;
        
    }
    
    // MANUALLY SET INFINITE MAXIMUM VALUE -----------------------------------//
    public void max_infinite( double value ){
        
        percentage_maximum = value;
        
    }
    
    // MANUALLY SET THE NAME OF THE PERCENTAGE AXIS --------------------------//
    public void percentage_name( String name ){
        
        name_percentage = name;
        
    }
    
    // MANUALLY SET THE NAME OF THE INSTANCES AXIS ---------------------------//
    public void instance_name( String name ){
        
        name_instances = name;
        
    }
    
    // MANUALLY SET THE NAME OF THE INSTANCES AXIS ---------------------------//
    public void distance_name( String name ){
        
        name_distances = name;
        
    }
    
    // MANUALLY SET HISTOGRAM HEIGHT -----------------------------------------//
    public void histogram_constrainheight( int constraint ){
        
        // SET CONSTRAINT VARIABLE FOR HEIGHT
        this.histogram_height = constraint;
        
        // SET PREFERRED & MINIMUM SIZE
        histogram_panel.setPreferredSize(new Dimension(histogram__width, constraint));
        histogram_panel.setMinimumSize(  new Dimension(histogram__width, constraint));
        
    }
    
    // MANUALLY SET HISTOGRAM WIDTH ------------------------------------------//
    public void histogram_constrainwidth( int constraint ){
                
        // SET CONSTRAINT VARIABLE FOR HEIGHT
        this.histogram__width = constraint;
        
        // SET PREFERRED & MINIMUM SIZE
        histogram_panel.setPreferredSize(new Dimension(constraint, histogram_height));
        histogram_panel.setMinimumSize(  new Dimension(constraint, histogram_height));
        
    }
    
    // MANUALLY SET HISTOGRAM HEIGHT -----------------------------------------//
    public void percentage_constrainheight( int constraint ){
        
        // SET CONSTRAINT VARIABLE FOR HEIGHT
        this.percentage_height = constraint;
        
        // SET PREFERRED & MINIMUM SIZE
        percentage_panel.setPreferredSize(new Dimension(percentage_width, constraint));
        percentage_panel.setMinimumSize(  new Dimension(percentage_width, constraint));
        
    }
    
    // MANUALLY SET HISTOGRAM WIDTH ------------------------------------------//
    public void percentage_constrainwidth( int constraint ){
                
        // SET CONSTRAINT VARIABLE FOR HEIGHT
        this.percentage_width = constraint;
        
        // SET PREFERRED & MINIMUM SIZE
        percentage_panel.setPreferredSize(new Dimension(constraint, percentage_height));
        percentage_panel.setMinimumSize(  new Dimension(constraint, percentage_height));
        
    }
    
    // RETURN HISTOGRAM PANEL INSTANCE ---------------------------------------//
    public JPanel retrieve_histogram(){
        
        // GENERATE PANEL IF NONE EXIST
        if( histogram_panel == null ){
            
            histogram_panel = new Histogram(this);
            
        }
        
        // RETURN HISTOGRAM INSTANCE
        return histogram_panel;
        
    }
    
    // RETURN PERCENTAGE PANEL INSTANCE --------------------------------------//
    public JPanel retrieve_percentage(){
        
        // GENERATE PANEL IF NONE EXIST
        if( percentage_panel == null ){
            
            percentage_panel = new Percentage(this);
            
        }
        
        // RETURN PERCENTAGE INSTANCE
        return percentage_panel;
        
    }
    
    //------------------------------------------------------------------------//
    // STATIC HISTOGRAM GRAPHICS CLASS :                                      //
    //------------------------------------------------------------------------//
    private static class Histogram extends JPanel {

        // PRIVATE VARIABLES FOR VISUALIZATION DIMENSIONS
        private int visualization_height;
        private int visualization_width;
        
        // PRIVATE VARIABLES FOR BORDER PADDING
        private final int border_top;
        private final int border_bottom;
        private final int border_left;
        private final int border_right;
        private final int border_vertical;
        private final int border_horizontal;
        
        // PRIVATE VARIABLES FOR VISUALIZATION COLORS
        private final Color colour_rectangle;
        private final Color colour_borders;
                    
        // PRIVATE VARIABLES FOR HISTOGRAM & VISUALIZATION DATA SIZE
        private int    histogram_size;
        private double visualize_size;
               
        // PRIVATE EXTERNAL CLASS INSTANCES
        private final APSPH_Visualization histogram;
        
                // PUBLIC CONSTRUCTOR ------------------------------------------------//
        public Histogram( APSPH_Visualization visualization ){

            // PASS PARENT CLASS INSTANCE ( ALLOWS CONTINUOUS UPDATING OF PROPERTIES ) )
            this.histogram = visualization;
            
            // ENABLE TRANSPARENCY OF VISUALIZATION BACKGROUND
            setOpaque( false );
            
            // SET COLOURS
            colour_rectangle = new Color(0xCC0000);
            colour_borders   = new Color(0x000000);
            
            // SET BORDER PADDING PROPERTIES ( IN TERMS OF PIXELS )
            border_top        = 15;
            border_bottom     = 50;
            border_left       = 15;
            border_right      = 75;
            border_vertical   = border_top  + border_bottom;
            border_horizontal = border_left + border_right;
            
        }
        
        // PAINT THE PANEL COMPONENTS ( CALLED BY GRAPHICS ) -----------------//
        @Override
        protected void paintComponent( Graphics graphic ){
            
            // LET SUPER DRAW SIMPLE COMPONENTS
            super.paintComponent(graphic);

            // SET VISUALIZATION DIMENSIONS
            visualization_dimension();

            // DRAW THE HISTOGRAM
            draw_histogram( (Graphics2D) graphic );
            
            // DRAW THE PERCENTAGE BORDER VISUALIZATION
            draw_borders( ( Graphics2D ) graphic );
            
            // DRAW THE LABELLING INFORMATION
            draw_labels(  ( Graphics2D ) graphic );
            
        }
        
        // CURRENT HISTOGRAM DIMENSION ---------------------------------------//
        private void visualization_dimension(){
            
            visualization_height = ( histogram.histogram_height > 0 ? histogram.histogram_height : getHeight() );
            visualization_width  = ( histogram.histogram__width > 0 ? histogram.histogram__width : getWidth()  );
            
        }
        
        // MANAGE DATA TO FIT VISUALIZATION WIDTH ----------------------------//
        private void draw_histogram( Graphics2D graphic ){
            
            // SKIP METHOD IF NO DATA RANGE EXISTS, ELSE SET DATA SIZE
            if( histogram.minimum_value == Double.POSITIVE_INFINITY || histogram.maximum_value == Double.NEGATIVE_INFINITY ){
                
                return;
                
            } else {
                
                histogram_size = histogram.histogram_data.size();
                
            }
            
            // SCALE THE HISTOGRAM, OR AVERAGE SUBLIST VALUES TO APPROXIMATE HISTOGRAM
            if( histogram_size < visualization_width ){
                
                // CREATE THE VISUALIZATION
                paint_rectangle( graphic, histogram.histogram_data );
                
                // SET THE VISUALIZATION DATA SIZE
                visualize_size = (double) histogram_size;
                
            } else {
                
                // APPROXIMATION DATA COLLECTION
                List<Double> approximate_data = new ArrayList<Double>();
                
                // VARIABLES FOR TWO SUBLIST SIZES
                int small_size = histogram_size / visualization_width;
                int large_size = small_size + 1;
                
                // VARIABLE FOR THE NUMBER OF SMALL SUBLISTS
                int sublist_numbers = visualization_width - ( histogram_size % visualization_width );
                
                // VARIABLE FOR THE NUMBER OF DATA HANDLED
                int data_handled = 0;
                
                // LOOP THROUGH DATA, GENERATING APPROXIMATE VALUES
                for ( int i = 0; i < visualization_width; i++ ){
                    
                    // SET LIST SIZE AND INITIALIZE AVERAGE VALUE
                    int    elements = ( i < sublist_numbers ? small_size : large_size );
                    double averages = 0.0;
                    
                    // LOOP THROUGH HISTOGRAM DATA, SUMMING VALUES
                    for ( int j = 0; j < elements; j++ ){
                        
                        averages += histogram.histogram_data.get( data_handled++ ).doubleValue();
                        
                    }
                    
                    // ADD AVERAGE TO APPROXIMATE COLLECTION
                    approximate_data.add( averages /= elements );
                    
                }
                
                // CREATE THE VISUALIZATION
                paint_rectangle( graphic, approximate_data );
                
                // SET THE VISUALIZATION DATA SIZE
                visualize_size = (double) approximate_data.size();
                
            }
                
        }
        
        // PAINT EACH RECTANGLE IN THE HISTOGRAM -----------------------------//
        private void paint_rectangle( Graphics2D graphic, List<Double> visualization_data ){
 
            // POSITIONAL INFORMATION VARIABLES
            int horizontal_position = border_left;
            
            // VARIABLES DERIVING RECTANGULAR SIZE
            int rectangle_width = (int) ( (visualization_width - border_horizontal) / visualization_data.size() );
            int rectangle_extra = (visualization_width - border_horizontal) - ( rectangle_width * visualization_data.size() );
            
            // LOOP THROUGH DATA AND PAINT EACH RECTANGLE INDIVIDUALLY
            for (Double datum : visualization_data) {
                
                // SET RECTANGLE DIMENSIONS
                int width  = rectangle_width + ( rectangle_extra > 0 ? 1 : 0 );
                int height = (int) ( datum / max( visualization_data ) * (visualization_height - border_vertical));
                
                // DECREMENT AMOUNT OF EXTRA WIDTH
                rectangle_extra--;
                
                // SET RECTANGLE COLORING
                graphic.setColor( colour_rectangle );
                
                // FILL RECTANGLE
                graphic.fillRect( horizontal_position, (visualization_height - border_bottom - height), width, height );
                
                // INCREMENT HORIZONTAL POSITION
                horizontal_position += width;
            }
        }
        
        // DRAW THE VISUALIZATION BORDERS ------------------------------------//
        private void draw_borders( Graphics2D graphic ){
        
            // SET GRAPHICS LINE WIDTH
            graphic.setStroke( new BasicStroke(1) );
            
            // SET GRAPHICS COLOUR
            graphic.setColor(colour_borders);
            
            // DRAW BOUNDARY BOX
            graphic.drawRect(border_left, border_top, (visualization_width - border_horizontal), (visualization_height - border_vertical) );

        }
        
        // LABEL THE VISUALIZATION -------------------------------------------//
        private void draw_labels( Graphics2D graphic ){
                     
            // SET THE LENGTH OF THE RANGE INDICATORS
            int indicator_length = 10;
            
            // INITIALIZE POSITION VARIABLES
            int vertical;
            int horizontal;
            
            // INITIALIZE VALUE VARIABLE
            double value;
            
            // LOOP THROUGH VERTICAL LABELS AND DRAW INFORMATION
            for ( int i = 0; i <= visualize_size; i++ ){

                // SET POSITION VARIABLES ( PRODUCES A '/ BY ZERO' ERROR? )
                horizontal = visualization_width - border_right;
                vertical   = (int) ( border_top + (double) ( (visualize_size - i) * (visualization_height - border_vertical) / visualize_size ) );

                // SET VERTICAL VALUE
                value = histogram.maximum_value * (double) i / visualize_size;
                
                // DRAW RANGE-LABELLING INDICATORS
                graphic.drawLine( horizontal, vertical, (horizontal + indicator_length), vertical);
                
                // DRAW VERTICAL VALUE LABELS
                graphic.drawString( Integer.toString( (int) value), (horizontal + indicator_length + 4), vertical );

            }
            
            // LOOP THROUGH HORIZONTAL LABELS AND DRAW RANGE INDICATORS
            for ( int i = 0; i < visualize_size; i++ ){
                
                // SET POSITION VARIABLES
                horizontal = (int) ( border_left + (double) ( i * (visualization_width - border_horizontal) / visualize_size ) );
                vertical   = visualization_height - border_bottom;
                
                // DRAW RANGE-LABELLING INDICATORS
                graphic.drawLine(horizontal, vertical, horizontal, (vertical + indicator_length));
                
            }
            
            // LOOP THROUGH HORIZONTAL LABELS AND DRAW INFORMATION
            for ( int i = 0; i < visualize_size; i++ ){

                // SET POSITION VARIABLES
                horizontal = (int) ( border_left + (double) ( i * (visualization_width - border_horizontal) / visualize_size ) );
                vertical   = visualization_height - border_bottom;
                
                // SET HORIZONTAL VALUE
                value = histogram_size * (double) i / visualize_size;

                // DRAW HORIZONTAL VALUE LABELS
                graphic.drawString( Integer.toString( (int) value), horizontal, (12 + vertical + indicator_length) );

            }
            
            // DRAW HORIZONTAL AXIS NAME
            graphic.drawString(histogram.name_distances, border_left, (visualization_height - 10) );

            // DRAW VERTICAL AXIS NAME
            graphic.translate( (visualization_width - 10), (visualization_height - border_bottom));
            graphic.rotate( -(Math.PI / 2) );
            graphic.drawString(histogram.name_instances, 0, 0 );
            graphic.rotate(  (Math.PI / 2) );
            graphic.translate( -(visualization_width - 10), -(visualization_height - border_bottom));

        }

    }
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
    
    //------------------------------------------------------------------------//
    // STATIC SINGULAR BAR GRAPHIC CLASS :                                    //
    //------------------------------------------------------------------------//
    private static class Percentage extends JPanel {
        
        // PRIVATE VARIABLES FOR VISUALIZATION DIMENSIONS
        private int visualization_height;
        private int visualization_width;
        
        // PRIVATE VARIABLE FOR PERCENTAGE RECTANGLE HEIGHT
        private int percentage_height;
        private int percentage_width;
        
        // PRIVATE VARIABLES FOR BORDER PADDING
        private final int border_top;
        private final int border_bottom;
        private final int border_left;
        private final int border_right;
        private final int border_vertical;
        private final int border_horizontal;
        
        
        // PRIVATE VARIABLES FOR VISUALIZATION COLORS
        private final Color colour_rectangle;
        private final Color colour_borders;
        
        // PRIVATE EXTERNAL CLASS INSTANCES
        private final APSPH_Visualization histogram;
        
        // PUBLIC CONSTRUCTOR ------------------------------------------------//
        public Percentage( APSPH_Visualization visualization ){
            
            // PASS PARENT CLASS INSTANCE ( ALLOWS CONTINUOUS UPDATING OF PROPERTIES ) )
            this.histogram = visualization;
            
            // ENABLE TRANSPARENCY OF VISUALIZATION BACKGROUND
            setOpaque( false );
            
            // SET COLOURS
            colour_rectangle = new Color(0xCC0000);
            colour_borders   = new Color(0x000000);
            
            // SET BORDER PADDING PROPERTIES ( IN TERMS OF PIXELS )
            border_top        = 15;
            border_bottom     = 50;
            border_left       = 10;
            border_right      = 75;
            border_vertical   = border_top  + border_bottom;
            border_horizontal = border_left + border_right;
            
        }
        
        // PAINT THE PANEL COMPONENTS ( CALLED BY GRAPHICS ) -----------------//
        @Override
        protected void paintComponent( Graphics graphic ){
            
            // LET SUPER DRAW SIMPLE COMPONENTS
            super.paintComponent( graphic );
            
            // SET VISUALIZATION DIMENSIONS
            visualization_dimension();
            
            // DRAW THE PERCENTAGE RECTANGLE VISUALIZATION
            draw_percentage( ( Graphics2D ) graphic );
            
            // DRAW THE PERCENTAGE BORDER VISUALIZATION
            draw_borders(    ( Graphics2D ) graphic );
            
            // DRAW THE LABELLING INFORMATION
            draw_labels(     ( Graphics2D ) graphic );
            
        }
        
        // CURRENT HISTOGRAM DIMENSION ---------------------------------------//
        private void visualization_dimension(){
            
            visualization_height = ( histogram.percentage_height > 0 ? histogram.percentage_height : getHeight() );
            visualization_width  = ( histogram.percentage_width  > 0 ? histogram.percentage_width  : getWidth()  );
            
        }

        // DRAW THE PERCENTAGE RECTANGLE -------------------------------------//
        private void draw_percentage( Graphics2D graphic ){
            
            // DETERMINE THE HEIGHT OF THE RECTANGLE VISUALIZATION
            percentage_height = (int) ( ( visualization_height - border_vertical   ) * histogram.percentage_amounts / histogram.percentage_maximum );
            percentage_width  = (int)   ( visualization_width  - border_horizontal );
            
            // SET GRAPHICS COLOUR
            graphic.setColor(colour_rectangle);
            
            // DRAW THE RECTANGLE VISUALIZATION
            graphic.fillRect( border_left, (visualization_height - border_bottom - percentage_height), percentage_width, percentage_height);
            
        }
        
        // DRAW THE BORDER VISUALIZATION -------------------------------------//
        private void draw_borders( Graphics2D graphic ){
            
            // SET GRAPHICS LINE WIDTH
            graphic.setStroke( new BasicStroke(1) );
            
            // SET GRAPHICS COLOUR
            graphic.setColor(colour_borders);
            
            // DRAW BOUNDARY BOX
            graphic.drawRect(border_left, border_top, percentage_width, (visualization_height - border_vertical) );
            
        }
        
        // DRAW THE LABELLING INFORMATION ------------------------------------//
        private void draw_labels( Graphics2D graphic ){
            
            // SET THE LENGTH OF THE RANGE INDICATORS
            int indicator_length = 10;
            
            // DRAW RANGE-LABELLING INDICATORS
            graphic.drawLine( (visualization_width - border_right),                                                 border_top, (visualization_width - border_right + indicator_length),                                                border_top );
            graphic.drawLine( (visualization_width - border_right),                     (visualization_height - border_bottom), (visualization_width - border_right + indicator_length),                     (visualization_height - border_bottom));
            graphic.drawLine( (visualization_width - border_right), (visualization_height - border_bottom - percentage_height), (visualization_width - border_right + indicator_length), (visualization_height - border_bottom - percentage_height));
            
            // DRAW LABELS
            graphic.drawString(                                             "0",  (visualization_width - border_right + indicator_length + 4),                     (visualization_height - border_bottom) );
            graphic.drawString( Long.toString( (long) histogram.percentage_maximum ), (visualization_width - border_right + indicator_length + 4),                                                border_top  );
            graphic.drawString( Long.toString( (long) histogram.percentage_amounts ), (visualization_width - border_right + indicator_length + 4), (visualization_height - border_bottom - percentage_height) );
            
            // DRAW VISUALIZATION NAME
            graphic.drawString(histogram.name_percentage, border_left, (visualization_height - 10) );
            
        }
        
    }
    //------------------------------------------------------------------------//
    //------------------------------------------------------------------------//
}
//============================================================================//
//============================================================================//
