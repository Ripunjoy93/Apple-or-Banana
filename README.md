## Is it a Apple or Banana !!!

**This is a PoC (proof of concept) model to train an image classifier using Spark MLlib**

### Project includes:
+ Useful functions to check the validity of an image.
+ Functions to resize, reshape images and extract their pixel values.
+ Building data-set to train an image classifier.
+ Training the model (PoC). *I generally use R, while finding the best model. Then use other big data tools to make it production ready if I am sure about the model performance. Usually I don't perform analysis, and validation part in Spark, though there is possibility to do so*
+ Classifying an incoming image. (given a image, we can make same RDD, and intead of test split can pass the incoming image RDD to predict)