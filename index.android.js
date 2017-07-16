import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  NativeModules,
  TouchableHighlight
} from 'react-native';

import SecondPage from './js/SecondPage'

class Main extends Component {

  _onPressButton(){
    NativeModules.NavigationModule.push("SecondPage",{})
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!!
        </Text>
        <TouchableHighlight onPress={this._onPressButton}>
              <Text>
                 Go to SecondPage!
              </Text>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    paddingHorizontal: 50,
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
    fontSize: 14,
    lineHeight: 22,
  },
});

AppRegistry.registerComponent('Main', () => Main);
AppRegistry.registerComponent('SecondPage', () => SecondPage);