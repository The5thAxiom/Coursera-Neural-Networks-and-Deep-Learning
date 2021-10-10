# Logistic Regression as a Neural Network
## Binary Classification
- Example:
    - For recognising cats, each image is either a cat(1) or not a cat(0) thus the images are classified into two (thus, binary classification).
    - Each input image is in form of 3 matrices (for the RGB values of each pixel)
    - $x$ is a feature vector which lists all the Red, Green and Blue values (if the image is 64 by 64 pixels, x will have $64 \times 64 \times 3$ values and thus, will be a $12288 \times 1$ matrix (or vector))
    - Thus, $n_x = 12288$ ($n_x$ might be written $n$ for brevity)
    - This NN will take in $x$ and produce the output $y$ which will either be $1$ or $0$.
- Notation:
    - $m$ or $m_{train}$ denote the number of training examples.
    - $m_{test}$ denotes the number of test examples.
    - A single training example: $(x, y)$ where $x \in \mathbb{R} ^{n_x}$ (x is an n_x dimensional feature vector) and $y \in \{0, 1\}$.
    - $m$ training examples: $(x_1, y_1), (x_2, y_2), (x_3, y_3), ... (x_m, y_m)$.
    - $X = [x_1\ x_2\ x_3\ ...\ x_m]$ where $X$ is a $n_x$ by $m_{train}$ matrix denoting all the training examples (running `X.shape` in python should give `(nx, m)`).
    - $Y = [y_1\ y_2\ y_3\ ...\ y_m]$ where $Y$ is  $1$ by $m$ matrix (running `Y.shape` in python should give `(1, m)`).
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
- 