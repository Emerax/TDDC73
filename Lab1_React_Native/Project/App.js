/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  Button,
  StyleSheet,
  View,
  Text,
  TextInput,
  Image
} from 'react-native';

import {Header} from 'react-native-elements';

const App: () => React$Node = () => {
  return (
    <>
      <Header
        placement="left"
        centerComponent={<Text style={styles.titleText}>TDDC73 Lab1, React-Native version</Text>}/>
      <View style={{justifyContent: "flex-start"}}>
        <View style={{alignItems: "center" }}>
          <Image source={require('./images/education.png')}/>
        </View>
        <View style={{justifyContent: "space-between"}}>
          <View style={{flexDirection: "row", justifyContent: 'space-around'}}>
            <View>
              <Button title="Button"/>
            </View>
            <View>
              <Button title="Button"/>
            </View>
          </View>
          <View style={{flexDirection: "row", justifyContent: 'space-around'}}>
            <View>
              <Button title="Button"/>
            </View>
            <View>
              <Button title="Button"/>
            </View>
          </View>
        </View>
        <View style={{flexDirection: "row", justifyContent: "flex-start", alignItems: "baseline"}}>
          <Text style={{flex: 1}}>Email:</Text>
          <TextInput style={{ flex: 5, alignSelf: "stretch"}} underlineColorAndroid={'rgb(0,0,0)'} autoCompleteType="email"/>
        </View>
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  titleText: {
  color: '#fff',
  fontSize: 20,
  fontWeight: 'bold',
  }
});

export default App;
