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
    - The 2nd node of the hidden layer $z^{[1]}_2 = {w^{[1]}_2}^{T} \cdot x + b^{[1]}_2$ and $a^{[1]}_2 = \sigma(z^{[1]}_2)$.
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
### Leaky ReLU
- $g(z) = \max(0.01 \times z, z)$
- $g\prime(z) = \frac{dg(z)}{dz} = \begin{cases} 0.01 & if z < 0 \\1 & if z > 0 \\ undefined & if z = 0\end{cases}$
## Gradient Descent for Neural Networks
### Parameters
- $W^{[1]}$, $b^{[1]}$, $W^{[2]}$ and $b^{[2]}$.
- $n_x = n^{[0]}$, $n^{[1]}$ and $n^{[2]} = 1$
- $J(W^{[1]},\ b^{[1]},\ W^{[2]},\ b^{[2]}) = \frac{1}{m}\sum_{i = 1}^n\mathfrak{L}(A^{[2]}, Y)$
### Steps
- Each iteration will repeat the following steps
- predict $A^{[2]}$ aka $\hat{Y}$
- calculate $dW^{[1]} = \frac{dJ}{dw^{[1]}},\ db^{[1]} = \frac{dJ}{db^{[1]}},\ \dots$
- $W^{[1]} = W^{[1]} - \alpha \cdot dW^{[1]}\\ \vdots$
- these are basically what we did in LR but with the square brackets (multiple nodes and multiple layers)
### Formulas
#### Forward Propogation
- $Z^{[1]} = {W^{[1]}}^T \cdot A^{[0]} + b^{[1]}$
- $A^{[1]} = g^{[1]}(Z^{[1]})$
- $Z^{[2]} = {W^{[2]}}^T \cdot A^{[1]} + b^{[2]}$
- $A^{[2]} = g^{[2]}(Z^{[2]}) = \sigma(Z^{[2]})$
#### Backward Propogation
- $dZ^{[2]} = A^{[2]} - Y$
- $dW^{[2]} = \frac{1}{m}dZ^{[2]}\cdot {A^{[1]}}^T$
- $db^{[2]} = \frac{1}{m} \cdot$`np.sum(`$dZ^{[2]}$`, axis = 1, keepdims = True)`
- $dZ^{[1]} = {W^{[2]}}^T\cdot dZ^{[2]} \times g^{[1]}\prime(Z^{[1]})$ ($\times$ is the element-wise product (`*` in numpy))
- $dW = \frac{1}{m}dZ^{[1]}\cdot X^T$
- $db^{[1]} = \frac{1}{m}$`np.sum(`$dZ^{[1]}$`, axis = 1, keepdims = True)`
## Random Initialization
### Why random?
- Initializing the w's and b's with zeroes works fine for logistic regression but won't work for a neural network.
- If we do initialize them with zeroes, every node in a layer will be doing, essentially the same thing, which defeats the entire purpose of having different layers and nodes.
- The nodes become "symmetric" which means they are giving the same values.
- Thus, we need to give w random values to prevent the symmetry problem.
### How
- $W^{[1]}$ `= np.random.randn(<whatever the shape is>)) * 0.01` (we multiply by this 'small value')
- $W^{[2]}$ `= np.random.randn(<whatever the shape is>)) * 0.01`
- the b's can still be all zeroes, that will not cause any symmetry.
### The small value:
- Used to keep the values of W small so we don't go to the flatter parts of the tanh and sigmoid functions.
- 0.01 works for a shallow network like this, but other lectures will get into selecting this value in more detail