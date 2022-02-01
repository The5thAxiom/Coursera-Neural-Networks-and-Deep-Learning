# Logistic Regression as a Neural Network
## Binary Classification
### Example:
- For recognising cats, each image is either a cat(1) or not a cat(0) thus the images are classified into two (thus, binary classification).
- Each input image is in form of 3 matrices (for the RGB values of each pixel)
- $x$ is a feature vector which lists all the Red, Green and Blue values (if the image is 64 by 64 pixels, x will have $64 \times 64 \times 3$ values and thus, will be a $12288 \times 1$ matrix (or vector))
- Thus, $n_x = 12288$ ($n_x$ might be written $n$ for brevity)
- This NN will take in $x$ and produce the output $y$ which will either be $1$ or $0$.
### Notation:
- $m$ or $m_{train}$ denote the number of training examples.
- $m_{test}$ denotes the number of test examples.
- A single training example: $(x, y)$ where $x \in \mathbb{R} ^{n_x}$ (x is an n_x dimensional feature vector) and $y \in \{0, 1\}$.
- $m$ training examples: $\{(x^{(1)}, y^{(1)}), (x^{(2)}, y^{(2)}), (x^{(3)}, y^{(3)}), ... (x^{(m)}, y^{(m)})\}$.
- $X = [x^{(1)}\ x^{(2)}\ x^{(3)}\ ...\ x^{(m)}]$ where $X$ is a $n_x$ by $m_{train}$ matrix denoting all the training examples (running `X.shape` in python should give `(nx, m)`).
- $Y = [y^{(1)}\ y^{(2)}\ y^{(3)}\ ...\ y^{(m)}]$ where $Y$ is  $1$ by $m$ matrix (running `Y.shape` in python should give `(1, m)`).
## Logistic Regression
- A learning algorithm used when $y$ (for supervised learning) is either $0$ or $1$.
- Given, $x$ we want, $\hat{y} = P(y=1|x)$ (the probability of y being $1$ given $x$).
- The parameters of logistic regression:
    - $w \in \mathbb{R}^{n_x}$
    - $b \in \mathbb{R}$
- Using linear regression, $\hat{y} = w^T \cdot x + b$. This will not work as it will produce values of $\hat{y}$ which are very large, very small or even negative, all of which are not possible for a probability ($\hat{y} \in \{0, 1\}$).
- Actual output, $\hat{y} = \sigma(w^T\cdot x + b)$
- $\sigma()$ is the _sigmoid_ function. $\sigma(z) = \frac{1}{1 + e^{-z}},\ where\ z \in \mathbb{R}$.
- Using this sort-of normalizes the large and small values of the linear regression formula, givin us a usable function for probability.
- What we need to do is to set $w$ and $b$ such that $\hat{y}$ becomes a good estimate of $P(y=1)$.
- There is also an alternate convention which won't be used here i think.
## Logistic Regression Cost Function
- We need to define a cost function to train the parameters $w$ and $b$.
- We know that, $\hat{y} = \sigma(w^T\cdot x + b)$ where $\sigma(z) = \frac{1}{1 + e^{-z}},\ iff\ z \in \mathbb{R}$.
- We want $\hat{y}_i \approx y_i$ for $m$ training examples $\{(x^{(1)}, y^{(1)}), (x^{(2)}, y^{(2)}), (x^{(3)}, y^{(3)}), ... (x^{(m)}, y^{(m)})\}$ remember that, ($\hat{y}^{(i)} = \sigma(w^T\cdot x^{(i)} + b)$)
### Loss or Error Function
- Used to measure how our algorithm is doing.
- We want the loss function to be as small as possible.
- $\mathfrak{L}(\hat{y},\ y) = -(y \log \hat{y} + (1-y)\log (1-\hat{y}))$.
    - for $y=1$, $\mathfrak{L}(\hat{y}, 1) = -\log \hat{y}$ (here, we want $\hat{y}$ to be large, but because it's a sigmoid function, it's largest value will be 1)
    - for $y=0$, $\mathfrak{L}(\hat{y}, 0) = -\log (1-\hat{y})$ (here, we want $\hat{y}$ to be as small as possible)
### Cost function:
- $J(w,\ b) = \frac{1}{m} \sum_{i=1}^{m}\mathfrak{L}(\hat{y}^{(i)},\ y^{(i)})$
- thus, $J(w,\ b) = -\frac{1}{m} \sum_{i=1}^{m}(y^{(i)} \log \hat{y}^{(i)} + (1-y^{(i)})\log (1-\hat{y}^{(i)}))$
- J = the average of the Loss function for the entire training set
- We want to find $w$ and $b$ which will minimize $J(w, b)$.
- What I think will happening: J tells us how bad the NN is doing, we want J to be as low as possible. So we make a NN which tries values of w and b and tries to minimise the cost function.
## Gradient Descent
- Used to train $w$ and $b$.
- We basically find the minima of $J(w, b)$ as it is convex a surface (in 3-d space where: $z = J(w, b);\ x = w;\ y = b$)
- We will initialise the values of $w$ and $b$, and go steeper until we reach the global minimum (or close enough to it)
> is this is going to be class 1st sem calc (which was scary)??.
- After initializing $w$, we do `w := w - alpha * dw` (where `dw` = $\partial w = \frac{\partial J(w, b)}{\partial w}$) in a loop until we reach the minima (this will go down until the minimum is reached) (alpha = learning rate)
- For $b$: `b := b - alpha * db` (where `db` = $\partial b = \frac{\partial J(w, b)}{\partial b}$) (and hope that the two values converge I guess?)
- Both $b$ and $w$ descend until the minimum value is found.
## Computation Graphs
- A way of organising computations into forward and backward passes.
- The forward pass results in the result and the backward pass results in the derivative.
- This part felt kinda useless, i don't understand why these are useful, which will hopefully not be the case in a while.
- While writing the code, represent something like $\frac{d(J)}{d(var)}$ as `dvar` (J is there implicitly)
- Example:
    - ```
        a = 5, b = 3, c = 2
        u = bc, v = a + u, J = 3v

        a------------V
                    v=a+u -------> J = 3v
        b---v        /\
            u=bc------|
        c---^
        ```
    - Finding the result:
        - u = 6
        - v = 11
        - J = 33
        - Here, we are going forwards
    - Finding the derivative (da): (remember, dJ/da=da)
        - dv
        - du
        - da
        - here, we are going backwards
## Logistic Regression Gradient Descent
- Assuming $n_X = 2$
### For a single example
- The equations we have:
    - $z = w^T\cdot x + b$
    - $\hat{y} = a = \sigma(z)$
    - $\mathfrak{L}(a, y) = -(y\log (a) + (1-y)\log (1-a))$
- ```
    x1-----|
    w1--v \/
        z=w1*x1+w2*x2+b -----> a = sigma(z) ---->L(a, y)
    x2--^ /\  /\
    w2----|   |
    b---------|
    ```
- Forwards to find value
- Derivative:
    - `da = -(y/a) + (y/(1-a))`
    - `dz = a - y`
    - `dw1 = x1*dz`
    - `dw2 = x2*dz`
    - `db = dz`
- I understand this now, good work Mr. Ng!
### For m examples
- The equations we have:
    - $J(w, b) = -\frac{1}{m} \sum_{i=1}^{m}(y^{(i)} \log a^{(i)} + (1-y^{(i)})\log (1-a^{(i)}))$
    - $z^{(i)} = w^T\cdot x^{(i)} + b$
    - $\hat{y}^{(i)} = a^{(i)} = \sigma(z^{(i)})$
- Also:
    - $\frac{\partial}{\partial w_1}J(w, b) = -\frac{1}{m} \sum_{i=1}^{m}\frac{\partial}{\partial w_1^{(i)}} (y^{(i)} \log a^{(i)} + (1-y^{(i)})\log (1-a^{(i)}))$
- Algorithm:
    - ```python
        J = 0
        dw1 = 0
        dw2 = 0
        db = 0
        for i = 1 to m:
            zi = wT*xi + b
            ai = sigma(zi)
            J += -(yi*log(ai) + (1-yi)*log(1-ai))
            dzi = ai - yi
            dw1 += x1i * dzi
            dw2 += x2i * dzi
            db += dzi
        J /= m
        dw1 /= m
        dw2 /= m
        db /= m
        w1 = w1 - alpha * dw1
        w2 = w2 - alpha * dw2
        b = b - alpha * db
        ```
    - this was for n = 2, for larger numbers of n, we will have to use loops
    - these steps were for just 1 descent and will be done multiple times
    - after all this, dw1 = dj/dw1 (used as an accumulator)
    - But, using for loops reduces efficiency and thus we will use vectorization to speed everything up.
    - I am guessing here's where numpy and pandas and all that good stuff comes in
    - The value of dw here is cumulative (idk what that means)

## Vectorization
- Instead of using a for loop for a whole array.
- Vectorization using numpy will allow us to work directly with matrices and things like taking transposes and such will be much easier
- Remember: don't use explicit for loops where you can use vectors.
An example vector in numpy.

```python
import numpy as np
a = np.array([1, 5, 5, 0])
print(a)
```
- For example, to apply an exponential to every element of a matrix:
``` python
v = np.array([3, 4, 5, 6])
u = np.exp(v)
print(u)
```
- Our logistic Regression Code:
```python
J = 0
dw1 = 0
dw2 = 0
db = 0
for i = 1 to m:
    zi = wT*xi + b
    ai = sigma(zi)
    J += -(yi*log(ai) + (1-yi)*log(1-ai))
    dzi = ai - yi
    dw1 += x1i*dzi
    dw2 += x2i*dzi
    db += dzi
J /= m
dw1 /= m
dw2 /= m
db /= m
w1 = w1 - alpha * dw1
w2 = w2 - alpha * dw2
b = b - alpha * db
```
- With vectors, it will be:
```python
J = 0
dw = np.zeros((n-x, 1))
db = 0
for i in range(m + 1):
    zi = wT*xi + b
    ai = sigma(zi)
    J += -(yi*log(ai) + (1-yi)*log(1-ai))
    dzi = ai - yi
    dw += xi*dzi
    db += dzi
J /= m
dw /= m
db /= m
w1 = w1 - alpha * dw1
w2 = w2 - alpha * dw2
b = b - alpha * db
```
- Now, all the loops have been removed!!

## Vectorizing Logistic Regression
### Forward Pass: 
- We compute all the z's, then the a's and so on.
- For m cases: $X = [x^{(1)}\ x^{(2)}\ ...\ x^{(i)}]$
- Thus, $Z = [z^{(1)}\ z^{(2)}\ ...\ z^{(i)}] = w^T \cdot X + [b\ b\ ...\ b] = [w^T\cdot x^{(1)} + b\ \  w^T\cdot x^{(2)} + b\ ...\ w^T\cdot x^{(i)} + b]$
- In code: `Z = np.dot(wT, x) + b` (so simple!!!)
- $A = [a^{(1)}\ a^{(2)}\ ...\ a^{(i)}] = \sigma(Z)$
- Thus, we don't need $n_x$ lines of code or a for loop to do all the calculations, numpy and vectors are very good for that.
### Backward Pass:
- $\because dz^{(1)} = a^{(1)} - y^{(1)}, dz^{(2)} = a^{(2)} - y^{(2)},\ \dots\\$
- $\therefore dZ = [dz^{(1)}\ dz^{(2)}\ \dots\ dz^{(i)}]\\$
- also, as $A = [a^{(1)}\ a^{(2)}\ \dots\ a^{(i)}]$
- and $Y = [y^{(1)}\ y^{(2)}\ \dots\ y^{(i)}]\\$
- $\therefore dZ = A-Y$
- `dZ = A - Y`
- In numpy: `db = np.sum(dZ)/m`
- and `dw =np.dot(x, Z)/m`
>w is the matrix with w1, w2, w3... for all n elements of x
### One iteration of Gradient Descent (m training examples):
```python
Z = np.dot(w.T, X) + b # 1 x m dot m x n
A = sigma(Z) # y cap for all data sets
dZ = A - Y
dw = np.dot(X, dZ.T)/m
db = np.sum(dZ)/m
w = w - alpha * dw
b = b - alpha * db
```
- This code however, will be under a for loop as we need to perform gradient descent multiple times
## What I understand till now:
- For a single example:
    - We have X and Y
    - In a loop:
        - We have some values of w and b
        - We then do a forward pass to get the value of J, which tells us how bad we're doing
        - Then we go backwards and get the values of dw and db (the slopes of the graphs)
        - Then we perform gradient descent for both w and b (hopefully to get to the minimum value of J)
## Broadcasting in Python and other stuff
- in Week2.ipynb
## 


