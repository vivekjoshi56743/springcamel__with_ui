import './App.css';
import { useState } from 'react';

function App() {
  const [data, setData] = useState();
  const [pageNumber, setPageNumber] = useState(1);
  const [indexes, setIndexes] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let apiUrl = 'http://localhost:8080/test/' + String(pageNumber)+"/";
    if (indexes.trim() !== '') {
      apiUrl +=   String(indexes);
    }
    else{
      apiUrl+="-1"
    }

    fetch(apiUrl, {
      method: 'GET',
      headers: headers,
    })
      .then(response => response.json())
      .then(info => {
        
        setData(info);
        if(info.length === 0){
          alert('no users available with given page number and applied filter ')
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (


    <div className="container">
      <div className="header">
        <p>Calling: <b>https://reqres.in/api/users </b> with parameter page = {pageNumber} and filtering Indexes {indexes}</p>
      </div>
      <form onSubmit={handleSubmit}>
        <label>
          Page Number:
          <input
            type="number"
            value={pageNumber}
            onChange={(e) => setPageNumber(e.target.value)}
          />
        </label>
        <br />
        <label>
          Indexes (comma-separated):
          <input
            type="text"
            value={indexes}
            onChange={(e) => setIndexes(e.target.value)}
          />
        </label>
        <br />
        <button type="submit">Fetch Data</button>
      </form>
      <div className="response-container">
        <pre>{JSON.stringify(data, null, 2)}</pre>
      </div>
    </div>
  );
}

export default App;
