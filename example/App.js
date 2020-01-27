/**
 * Sample React Native App
 *
 * adapted from App.js generated by the following command:
 *
 * react-native init example
 *
 * https://github.com/facebook/react-native
 */

import React, {Component} from 'react';
import {Button, StyleSheet, Text, SafeAreaView, ScrollView} from 'react-native';
import {ImagePickerModule} from 'react-native-aztec';

export default class App extends Component<{}> {
  state = {
    status: 'starting',
    message: '--',
    aztecData: null,
  };
  render() {
    const {aztecData} = this.state;
    return (
      <SafeAreaView style={styles.container}>
        <ScrollView
          style={styles.container}
          contentContainerStyle={styles.contentContainer}>
          {aztecData && <Text>{aztecData.replace(/\|/g, '\n')}</Text>}
          <Button
            onPress={async () => {
              try {
                const aztecData = await ImagePickerModule.pickImage();
                this.setState({aztecData});
              } catch (e) {
                console.log(e);
              }
            }}
            title={'Pick'}
          />
        </ScrollView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  contentContainer: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
