# Shallow Neural Networks (One hidden layer)
## Overview
- $x^{(i)}$ meant the ith example, here $w^{[i]}$ refers to the ith layer of the Neural Network.
- steps:
    - $z^{[1]} = W^{[1]} \cdot x + b^{[1]}$
    - $a^{[1]} = \sigma(z^{[1]})$
    - $z^{[2]} = W^{[2]} \cdot a^{[1]} + b^{[2]}$
    - $a^{[2]} = \sigma(z^{[2]})$
    - $\mathfrak{L}(a^{[2]}, y)$
- In a neural network, the Z, A, L calculation happen multiple times
## Representation
- Look at [this](https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Neural_network.svg/600px-Neural_network.svg.png) image for reference.
- The green nodes are the inputs (x1, x2, x3), the blue ones are hidden layers and the yellow one is the output layers.
- What we see are the input and output layer and do not see the hidden layer.
- $a^{[i]}$ will be the value the $i^{th}$ layer passes on to the next layer. Thus, $a^{[0]} = x$ and $a^{[2]} = \hat{y}$
- In the reference image, we do not count the input layer and thus it is a 2-layer neural network (the hidden layer is the first one and the output layer is the second one).
- Parameters:
    - Layer one will have parameters $w^{[1]}$ and $b^{[1]}$.
    - The output layer will have the parameters $w^{[2]}$ and $b^{[2]}$.
## Ouptut Computation
### For a single example
- A simple logistic regression is does two things: calculating Z and A.
- Each node of a NN does logistic regression.
- For example:
    - The 1st node of the hidden layer: $z^{[1]}_1 = {w^{[1]}_1}^{T} \cdot x + b^{[1]}_1$ and $a^{[1]}_1 = \sigma(z^{[1]}_1)$.
    - The 2nd node of the hidden layer$z^{[1]}_2 = {w^{[1]}_2}^{T} \cdot x + b^{[1]}_2$ and $a^{[1]}_2 = \sigma(z^{[1]}_2)$.
    - and so on
- The hidden layer outputs: $a^{[1]}_1$, $a^{[1]}_2$, $a^{[1]}_3$ and $a^{[1]}_4$
- By stacking them in a column vector, we will get $z^{[1]} = {W^{[1]}}^T \cdot a^{[0]} + b^{[1]}$ and $a^{[1]} = \sigma(z^{[1]})$ for the 1st layer and $z^{[2]} = {W^{[2]}}^T \cdot a^{[0]} + b^{[2]}$ and $a^{[2]} = \sigma(z^{[1]})$ for the 2st layer.
### For Multiple Training Examples
- Final vectors:
    - Unvectorized: $z^{[1](i)}$, $a^{[1](i)}$, $z^{[2](i)}$ and $a^{[2](i)}$ (where i goes from $1$ to $m_{train}$)
    - Vectorized: $Z^{[1]} = {W^{[1]}}^T \cdot A^{[0]} + b^{[1]}$, $A^{[1]} = \sigma(Z^{[1]})$, $Z^{[2]} = {W^{[2]}}^T \cdot A^{[2]} + b^{[2]}$ and $A^{[2]} = \sigma(Z^{[2]})$
> NOTE: in the matrices, examples are set horizontally and different nodes, vertically (for LR, X had each pixel vertically which are the different nodes of the input layer, so its the same thing, now more generalized and with more than one node in the hidden layer).
##  Activation Functions
### Examples
- The sigmoid function used before is an activation function.
- There are many alternatives to it.
- For example, $\tanh(z)$ almost (it is good for the output layer of a binary classification) always works better than the sigmoid.
- The ReLU (Rectified Linear Unit: $a = max(0, z))$ is the most common activation function.
- Leaky ReLU: $a = max(0.01 \times z, z)$
### Need
- Why do we need an activation function, why not let z be the final output???
- If the output is linear, then it sort-of doesn't matter how many layers are there as by simplifying the equations for say $a^{[2]}$ is just a simple logistic regression. Thus, the NN becomes useless 'cause itni layers ka koi fayda hi nahi hua, befaltu me complexity badh rahi hai.
### Derivatives
#### Sigmoid
- $g(z) = \frac{1}{1 + e^{-z}}$
- $g\prime(z) = \frac{dg(z)}{dz} = g(z)(1 - g(z))$
- Examples:
    - if z = 10, $g(z) \approx 1$ and $g\prime(z) \approx 0$
    - if z = -10, $g(z) \approx 0$ and $g\prime(z) \approx 0$
    - if z = 0, $g(z) \approx \frac{1}{2}$ and $g\prime(z) \approx \frac{1}{4}$
#### $\tanh$
- $g(z) = \tanh{z} = \frac{e^z - e^{-z}}{e^z + e^{-z}}$
- $g\prime(z) = \frac{dg(z)}{dz} = 1 - (\tanh(z))^2$
- Examples:
    - if z = 10, $g(z) \approx 1$ and $g\prime(z) \approx 0$
    - if z = -10, $g(z) \approx -1$ and $g\prime(z) \approx 0$
    - if z = 0, $g(z) = 0$ and $g\prime(z) = 1$
#### ReLU
- $g(z) = \max(0, z)$
- $g\prime(z) = \frac{dg(z)}{dz} = \begin{cases} 0 & if z < 0 \\1 & if z > 0 \\ undefined & if z = 0\end{cases}$
### Leanky ReLU
- $g(z) = \max(0.01 \times z, z)$
- $g\prime(z) = \frac{dg(z)}{dz} = \begin{cases} 0.01 & if z < 0 \\1 & if z > 0 \\ undefined & if z = 0\end{cases}$
## Gradient Descent for Neural Networks
- Parameters:
    - 
## Random Initialization