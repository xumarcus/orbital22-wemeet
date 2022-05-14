import { useState, useEffect } from 'react';

function App() {
    const [state, setState] = useState('Hi');
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch('/api/hello', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            const json = await response.json();
            setState(json);
        }
        fetchData();
    }, []);
    return <p>{JSON.stringify(state)}</p>;
}

export default App;
