# Deep Neural Networks
## Deep $L$-Layer Neural Network
- Logistic regression is a very shallow NN, while one with say 5 layers is a relatively deep NN.
- Deeper NNs can do some stuff which is too difficult for a shallow one.
- The notation is basically the same.
## Forward Propagation
- Vectorized formulas:
    - $Z^{[l]} = {W^{[l]}}^T \cdot A^{[l-1]} + b^{[l]}$
    - $A^{[l]} = g^{[l]}(Z^{[l]})$
- These must be done for each layer in a for-loop, we can't vectorize this one.
## Matrix Dimensions
- Keeping the dimensions to all these matrices can be useful for debugging and understanding.
- Dimensions:
    - $A^{[l]}$ and $Z^{[l]}: (n^{[l]}, m_{train})$.
    - $W^{[l]}: (n^{[l]}, n^{[l-1]})$.
    - $b^{[l]}: (n^{[l]}, 1)$ (b gets broadcasted to $(n^{[l]}, m_{train})$ in python).
## The Need of Deep Neural Networks
- The depth of a NN can make it much better.
- This is basically [3Blue1Brown's NN Video](https://youtu.be/aircAruvnKk).
- Circuit theory and deep learning:
    - There are functions computable by L-layer NNs which a shallower one night need exponentially more hidden units (not layers) to compute.
- Rebranding 'neural networks with many hidden layers' as 'deep learning' has also increased the demand for deep learning (atleast somewhat).
- There are some problems, where even dozens of hidden layers are needed, though most will do with a couple or a handful.
## Building Blocks of Deep NNs
- Mind blown, can't write that shit in text. omg
## Forward and Backward Propagation
- Forward($A^{[l-1]}$)
    - $Z^{[l]}$ = np.dot($W^{[l]}$, $A^{[l-1]}$) + $b^{[l]}$
    - $Z^{[l]}$ = $g^{[l]}$($Z^{[l]}$)
    - return cache{$Z^{[l]}$, $W^{[l]}$, $b^{[l]}$}
- Backward($dA^{[l]}$, cache)
    - $dZ^{[l]}$ = $dA^{[l]}$ * $g^{[l]}$'($Z^{[l]}$)
    - $dW^{[l]}$ = np.dot($dZ^{[l]}$, $A^{[l-1]}$.T) / $m_{train}$
    - $db^{[l]}$ = np.sum($dZ^{[l]}$, axis = 1, keepdims = True) / $m_{train}$
    - $dA^{[l-1]}$ = np.dot($W^{[l]}$.T, $dZ^{[l]}$)
    - return $dA^{[l-1]}$, $dW^{[l]}$, $db^{[l]}$
- GradientDescent($dW^{[l]}$, $db^{[l]}$, learning_rate)
    - $W^{[l]}$ -= $dw^{[l]}$ * learning_rate
    - $b^{[l]}$ -= $db^{[l]}$ * learning_rate
    - return $W^{[l]}$, $b^{[l]}$
- We can get the cost using $A^{[L]}$ and $Y$.
- $dA^{[L]}$ = -$Y$ / $A^{[L]}$ + (1-$Y$) / (1-$A^{[L]}$)
## Parameters vs Hyperparameters
- Parameters: W and b for each layer
- Hyperparameters: learning_rate, the number of iterations, the number of hidden layers, the number of hidden units, activation function (ReLU, tanh, sigmoid)
- Deep learning is very trial and error based, the hyperparameters are adjusted to see what works.
## Neural Networks and Brains