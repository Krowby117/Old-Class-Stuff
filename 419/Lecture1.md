# Lecture 1
#### September 4th, 2025

Key Tools:
- Python + libs
- Jupyter Notebooks
- Git/Github

Goals:
- Establish a methodlogy to choose, test, and confirm our choice in models

### Why is modeling used in data science?
- The study of intrinsic patterns that are hidden in real life data

### Why do we use data modeling?
- Modeling allows for a systematic and logical approach to computational representation of data to analyze information and seek out meaningful patters in data

# Types of modeling:
- Statistical Models
  - Mathematical / probalistic modeling
  - Uncertainty distributions
  - Used in prediction, hypothesis testing, and estimation
- Graphical Models
  - Structual / Relational modeling
  - Dependencies, relationships, structures
  - Used in Networks, casual inference, and knowledge graphs

### Foundation of statistical models:
- Probability : num of occurences / total num of possibilities
- Independent Events : Events that occur without affecting the outcome of the next event
  - If you flip a coin 50 times and get 49 heads, whats the probability the last flip is heads?
- Dependent Events : Events that affect the outcome of the next event
  - Ex: With a normal deck of 52 cards, if you draw a 10 of clubs first, whats the probability that the next card is a club?
  - Conditional Probability P(A|B) = P(A ∩ B) / P(B)

### Foundation of graphical models:
- Data points are vertices
- The relationship between data points are edges
  - Can be directional and multiple edges, or simple graphs
- What defines the components of a graph given a data set?
  - Key points of interest are assigned as the vertices
  - The supposed relation between data point is the edge between two vertices
- What are the vertices and edges? What type of edges? Do we allow multiple edges? How would this data be formatted?