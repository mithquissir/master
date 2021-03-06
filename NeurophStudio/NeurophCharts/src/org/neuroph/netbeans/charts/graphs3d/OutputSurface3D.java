package org.neuroph.netbeans.charts.graphs3d;

import java.util.Arrays;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.nugs.graph3d.api.Point3D;
import org.nugs.graph3d.JzySurface3DFactory;
import org.nugs.graph3d.api.Surface3DFactory;
import org.nugs.graph3d.api.Surface3DProperties;
import org.neuroph.netbeans.charts.providers3d.OutputDataProvider3D;
import org.nugs.graph3d.api.Range;

/**
 * Surface chart of the network output for all rows of the dataset
 *
 * @author Vedrana Gajic
 */
public class OutputSurface3D extends Graph3DBuilder {

    private Point3D[] points3d, points3dCopy;
    private int dataSetRowCount;
    private NeuralNetwork neuralNet;

    public OutputSurface3D(NeuralNetwork nnet, DataSet dataset) {
        this.neuralNet = nnet;
        dataSetRowCount = dataset.getRows().size();
        dataProvider3D = new OutputDataProvider3D(dataset, nnet);
    }

    @Override
    public Chart createGraph() {
        points3d = (Point3D[]) dataProvider3D.getData(attribute1);
        
        // add a copy of the last neuron outputs in order to display surface nicely
        points3dCopy = Arrays.copyOf(points3d, points3d.length+dataSetRowCount);
        for(int p=0; p<dataSetRowCount; p++) {
           points3dCopy[points3d.length+p] = points3d[points3d.length-dataSetRowCount+p];
        }
        
        Surface3DProperties prop = new Surface3DProperties();
        IColorMap map = new ColorMapHotCold();
        map.setDirection(true);
        prop.setChartColor(map);
        prop.setChartQuality(Quality.Nicest);
        prop.setChartWireframed(true);
        int outputNeuronCount = neuralNet.getLayerAt(attribute1.getIndex()).getNeuronsCount();
        prop.setyRange(new Range(1, outputNeuronCount+1)); // repeat values for last neuron in order to display surface nicely
        prop.setyAxeInteger(true);

        prop.setxRange(new Range(1, dataSetRowCount));
        prop.setxAxeInteger(true);

        prop.setxAxeLabel("Dataset row"); //Dataset row/chosen attr
        prop.setyAxeLabel("Neuron"); //output neuron
        prop.setzAxeLabel("Output"); //err/out

        Surface3DFactory<Chart> surfaceFactory = new JzySurface3DFactory();
        Chart chart = surfaceFactory.createSurface(new Mapper() {
            @Override
            public double f(double x, double y) {
                for (int i = 1; i < points3dCopy.length; i++) {
                    Point3D point = points3dCopy[i];
                    if ((point.getX() == (int) x) && (point.getY() == (int) y)) {
                        return point.getZ();
                    }

                }
                return 0;
            }
        }, prop);

        return chart;

    }

    @Override
    public String toString() {
        return "Network outputs for entire dataset";
    }
}
