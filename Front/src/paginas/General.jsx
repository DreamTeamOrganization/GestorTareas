import React from 'react';

const General = ({ projectData, members }) => {
  return (
    <>
      <h2>{projectData?.name || 'Proyecto'}</h2>
      <p>{projectData?.description || ''}</p>

      <h4>Miembros del proyecto</h4>
      {!Array.isArray(members) || members.length === 0 ? (
        <p>No hay miembros en este proyecto.</p>
      ) : (
        <>
          {members.map((member) => (
            <p key={member.id}>{member.username}</p>
          ))}
        </>
      )}
    </>
  );
};

export default General;
