package org.neuroph.contrib.convolution;

import org.neuroph.core.Neuron;
import org.neuroph.core.input.Max;
import org.neuroph.core.transfer.Linear;
import org.neuroph.util.NeuronFactory;
import org.neuroph.util.NeuronProperties;

public class PoolingLayer extends FeatureMapsLayer {

    private static final long serialVersionUID = -6771501759374920877L;

    public PoolingLayer(Kernel kernel, Layer2D.Dimension dimension) {
        super(kernel, dimension);

        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty("useBias", false);
        neuronProperties.setProperty("transferFunction", Linear.class);
        neuronProperties.setProperty("inputFunction", Max.class);

        for (int i = 0; i < dimension.getHeight() * dimension.getWidth(); i++) {
            Neuron neuron = NeuronFactory.createNeuron(neuronProperties);
            addNeuron(neuron);
        }
    }

    // @Override
    // public void connectTo(FeatureMapsLayer toLayer, int fromFeatureMapIndex,
    // int toFeatureMapIndex) {
    // FeatureMap fromMap = getFeatureMap(fromFeatureMapIndex);
    // FeatureMap toMap = toLayer.getFeatureMap(toFeatureMapIndex);
    //
    // // Max inputFunction = new Max();
    // // TransferFunction transferFunction = new PoollingTransferFunction();
    //
    // int kernelWidth = kernel.getWidth();
    // int kernelHeight = kernel.getHeight();
    //
    // for (int x = 0; x < fromMap.getWidth() - kernelWidth + 1; x +=
    // kernelWidth) {
    // for (int y = 0; y < fromMap.getHeight() - kernelHeight + 1; y +=
    // kernelHeight) {
    // Weight weight = new Weight();
    // weight.setValue(1);
    // Neuron toNeuron = toMap.getNeuronAt(x / kernelWidth, y / kernelHeight);
    // for (int dy = 0; dy < kernelHeight; dy++) {
    // for (int dx = 0; dx < kernelWidth; dx++) {
    // int fromX = x + dx;
    // int fromY = y + dy;
    // Neuron fromNeuron = fromMap.getNeuronAt(fromX, fromY);
    // ConnectionFactory.createConnection(fromNeuron, toNeuron, weight);
    // // toNeuron.setInputFunction(inputFunction);
    // // toNeuron.setTransferFunction(transferFunction);
    // }
    // }
    // }
    // }
    // }

}
