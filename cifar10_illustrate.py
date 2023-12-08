import pickle
import numpy as np
import matplotlib.pyplot as plt
from random import random

import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import keras

learning_rate = 0.008

def unpickle(file):
    with open(file, 'rb') as f:
        dict = pickle.load(f, encoding="latin1")
    return dict


datadict_test = unpickle('cifar-10-batches-py/test_batch')
datadict1 = unpickle('cifar-10-batches-py/data_batch_1')
datadict2 = unpickle('cifar-10-batches-py/data_batch_2')
datadict3 = unpickle('cifar-10-batches-py/data_batch_3')
datadict4 = unpickle('cifar-10-batches-py/data_batch_4')
datadict5 = unpickle('cifar-10-batches-py/data_batch_5')

X_tr = np.concatenate((datadict1['data'], datadict2['data'], datadict3['data'],
                       datadict4['data'], datadict5['data']), axis=0)

Y_tr = datadict1["labels"] + datadict2["labels"] + datadict3["labels"] + \
       datadict4["labels"] + datadict5["labels"]

X_test = datadict_test["data"]
Y_test = np.array(datadict_test['labels'])

labeldict = unpickle('cifar-10-batches-py/batches.meta')
label_names = labeldict["label_names"]

#X = X_tr.reshape(10000, 3, 32, 32).transpose(0,2,3,1).astype("uint8")
Y = np.array(Y_tr)
Y_tr = np.array(Y_tr)
X_tr = np.array(X_tr)

print(Y_tr.shape)
print(X_tr.shape)
'''
for i in range(X.shape[0]):
    # Show some images randomly
    if random() > 0.999:
        plt.figure(1)
        plt.clf()
        plt.imshow(X[i])
        plt.title(f"Image {i} label={label_names[Y[i]]} (num {Y[i]})")
        plt.pause(1)
'''

model = Sequential()
# 5 neurons for input data
model.add(Dense(1000, input_dim=3072, activation='sigmoid'))
# 10 output neurons for output
model.add(Dense(10, activation='sigmoid'))

opt = keras.optimizers.SGD(lr=learning_rate)
model.compile(optimizer=opt, loss='mse', metrics=['mse'])

Y_tr_2 = np.empty([Y_tr.shape[0], 10])
indices = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
depth = 10
one_hot_matrix = tf.one_hot(indices, depth)

for i in range(0, 10):
    Y_tr_2[np.where(Y == i)] = one_hot_matrix[i]

tr_hist = model.fit(X_tr, Y_tr_2, epochs=45, verbose=0)

plt.plot(tr_hist.history['loss'])
plt.ylabel('loss')
plt.xlabel('epoch')

y_tr_pred = np.empty(Y_test.shape)
y_tr_pred_2 = np.squeeze(model.predict(X_test))

for pred_ind in range(y_tr_pred_2.shape[0]):
    index = np.argmax(y_tr_pred_2[pred_ind])
    y_tr_pred[pred_ind] = index

tot_correct = len(np.where(Y_test - y_tr_pred == 0)[0])
print(f'Classication accuracy (training data): {tot_correct / len(Y_test) * 100}%')
plt.show()
